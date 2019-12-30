package com.patchworkmc.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.patchworkmc.logging.LogLevel;
import com.patchworkmc.logging.Logger;

public class TaskScheduler {
	private final Logger logger;
	private final int cores;

	private final ThreadGroup threadGroup;
	private final Thread[] threads;

	private final AtomicBoolean shutdown;

	private final List<Task> tasks;
	private final Object lock;

	private final CountDownLatch countDownLatch;

	private final Map<Task, List<TaskOperation>> taskOperationsByTask;
	private final Task[] runningTasks;

	public TaskScheduler(Logger logger, int cores) {
		this.logger = logger;
		this.cores = cores;

		threadGroup = new RestartingThreadGroup("Task Threads");
		threads = new Thread[cores];

		shutdown = new AtomicBoolean(false);

		tasks = new ArrayList<>();
		lock = new Object();

		countDownLatch = new CountDownLatch(cores);

		taskOperationsByTask = new ConcurrentHashMap<>();
		runningTasks = new Task[cores];
	}

	public void start() {
		logger.info("Starting scheduler");
		shutdown.set(false);

		for (int i = 0; i < cores; i++) {
			threads[i] = new RunnerThread(threadGroup, logger.sub("Task runner " + (i + 1)), i);
			threads[i].start();
		}

		logger.info("Done!");
	}

	public void shutdown() {
		logger.info("Requesting scheduler stop.");
		shutdown.set(true);
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public boolean isShuttingDown() {
		return shutdown.get();
	}

	public boolean isRunning() {
		return threadGroup.activeCount() > 0;
	}

	public void forceShutdown() {
		if (!isRunning()) {
			return;
		}

		if (!isShuttingDown()) {
			throw new IllegalStateException("Tried to forcefully shutdown scheduler without requesting normal shutdown");
		}

		logger.warn("Forcefully stopping scheduler...");
		threadGroup.interrupt();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			logger.error("Failed to wait 2 seconds!");
			logger.thrown(LogLevel.ERROR, e);
		}

		if (isRunning()) {
			logger.warn("Failed to forcefully stop task runners with interrupt, forcing via stop()!");

			for (Thread t : threads) {
				t.stop();
			}

			if (isRunning()) {
				logger.error("Failed to stop task scheduler even with stop()!");
			}
		} else {
			logger.info("Done!");
		}
	}

	/**
	 * Schedules a new task to run later. The new task does not affect the
	 * current Task operation (if any).
	 *
	 * @param t The task to schedule
	 * @return The task operation triggered by scheduling the task
	 */
	public TaskOperation schedule(Task t) {
		return schedule(t, false, null);
	}

	/**
	 * Schedules a new task to run later. Depending on the parameters, the
	 * current Task operation (if any) may be extended with the newly scheduled task.
	 *
	 * @param t                       The new task to schedule
	 * @param expandsCurrentOperation If true, the current Task operation (if any) will be expanded, meaning
	 *                                it will finish only when the newly scheduled task has also finished.
	 * @return The task operation triggered by scheduling the task
	 */
	public TaskOperation schedule(Task t, boolean expandsCurrentOperation) {
		return schedule(t, expandsCurrentOperation, null);
	}

	/**
	 * Schedules a new task to run later. Depending on the parameters, the
	 * current Task operation (if any) may be extended with the newly scheduled task.
	 *
	 * @param t                       The new task to schedule
	 * @param expandsCurrentOperation If true, the current Task operation (if any) will be expanded, meaning
	 *                                it will finish only when the newly scheduled task has also finished.
	 * @param targetOperation         The operation the new task will be scheduled on, may be null
	 * @return {@code targetOperation} if it is not null, else the task operation triggered by scheduling
	 */
	public TaskOperation schedule(Task t, boolean expandsCurrentOperation, TaskOperation targetOperation) {
		if (shutdown.get()) {
			throw new IllegalStateException("Tried to schedule task after shutting down scheduler");
		}

		if (targetOperation == null) {
			targetOperation = new TaskOperation();
		}

		Thread currentThread = Thread.currentThread();
		List<TaskOperation> currentOperations = null;

		if (expandsCurrentOperation) {
			if (currentThread instanceof RunnerThread) {
				currentOperations = taskOperationsByTask.get(runningTasks[((RunnerThread) currentThread).runnerIndex]);
			}
		}

		List<TaskOperation> taskOperations = getOrCreateOperationsList(t);

		if (!taskOperations.contains(targetOperation)) {
			taskOperations.add(targetOperation);
		}

		if (currentOperations != null) {
			taskOperations.addAll(currentOperations);
		}

		taskOperations.forEach(TaskOperation::newScheduled);

		synchronized (lock) {
			tasks.add(t);
			lock.notifyAll();
		}

		return targetOperation;
	}

	public void expandCurrentOperation(TaskOperation... toAdd) {
		Thread currentThread = Thread.currentThread();

		if (currentThread instanceof RunnerThread) {
			List<TaskOperation> currentOperations =
					taskOperationsByTask.get(runningTasks[((RunnerThread) currentThread).runnerIndex]);
			currentOperations.forEach((op) -> op.expandWith(toAdd));
		}
	}

	private List<TaskOperation> getOrCreateOperationsList(Task t) {
		List<TaskOperation> operationList;

		if (taskOperationsByTask.containsKey(t)) {
			operationList = taskOperationsByTask.get(t);
		} else {
			operationList = new ArrayList<>();
			taskOperationsByTask.put(t, operationList);
		}

		return operationList;
	}

	private class RunnerThread extends Thread {
		private final Logger logger;
		private final int runnerIndex;

		RunnerThread(ThreadGroup threadGroup, Logger logger, int runnerIndex) {
			super(threadGroup, "TaskSchedulerRunner" + runnerIndex);
			this.logger = logger;
			this.runnerIndex = runnerIndex;
		}

		@Override
		public void run() {
			logger.debug("Runner started.");

			while (!shutdown.get() || !tasks.isEmpty()) {
				Task task;
				synchronized (lock) {
					try {
						Optional<Task> optionalTask = tasks.stream().filter(Task::canRun).findAny();

						if (!optionalTask.isPresent()) {
							lock.wait();
							continue;
						}

						task = optionalTask.get();
					} catch (InterruptedException e) {
						if (shutdown.get()) {
							break;
						} else {
							logger.error("Failed to wait on lock!");
							logger.thrown(LogLevel.ERROR, e);
						}

						continue;
					}

					tasks.remove(task);
				}

				if (task.isCanceled()) {
					continue;
				}

				try {
					runningTasks[runnerIndex] = task;
					task.step(logger);

					if (!task.isDone()) {
						synchronized (lock) {
							tasks.add(task);
						}
					} else {
						List<TaskOperation> operations = taskOperationsByTask.get(task);
						taskOperationsByTask.remove(task);

						operations.forEach((op) -> op.oneSucceeded(TaskScheduler.this));
					}
				} catch (Throwable t) {
					logger.error("Task %s failed!", task.name());
					logger.thrown(LogLevel.ERROR, t);

					List<TaskOperation> operations = taskOperationsByTask.get(task);
					taskOperationsByTask.remove(task);

					operations.forEach((op) -> op.oneFailed(t, TaskScheduler.this));
				}

				runningTasks[runnerIndex] = null;
			}

			logger.debug("Runner terminating.");
			synchronized (lock) {
				lock.notifyAll();
			}

			countDownLatch.countDown();
		}
	}

	private class RestartingThreadGroup extends ThreadGroup {
		RestartingThreadGroup(String name) {
			super(name);
		}

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			if (!(t instanceof RunnerThread)) {
				super.uncaughtException(t, e);
				return;
			}

			if (e instanceof OutOfMemoryError) {
				logger.error("Task runner went out of memory!");
				logger.thrown(LogLevel.ERROR, e);
				super.uncaughtException(t, e);
			} else {
				logger.warn("Task runner crashed, trying to start again...");
				int indexOfCrashed = ((RunnerThread) t).runnerIndex;

				threads[indexOfCrashed] =
						new RunnerThread(threadGroup, logger.sub("Task runner " + (indexOfCrashed + 1)), indexOfCrashed);
				threads[indexOfCrashed].start();
				logger.info("Task runner restarted.");
			}
		}
	}

	public boolean awaitShutdown(long timeout, TimeUnit unit) throws InterruptedException {
		return countDownLatch.await(timeout, unit);
	}
}
