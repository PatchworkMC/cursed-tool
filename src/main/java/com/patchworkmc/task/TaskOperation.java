package com.patchworkmc.task;

import java.util.HashMap;
import java.util.Map;

import com.patchworkmc.util.IThrowingConsumer;
import com.patchworkmc.util.IThrowingRunnable;

/**
 * Used for awaiting tasks.
 */
public class TaskOperation {
	enum State {
		RUNNING,
		DONE,
		FAILED
	}

	private final Map<TaskOperation, IThrowingRunnable<Throwable>> finishListeners;
	private final Map<TaskOperation, IThrowingConsumer<Throwable, Throwable>> failListeners;

	private int runningCount;
	private State state;
	private TaskScheduler finishScheduler;
	private Throwable error;

	TaskOperation() {
		finishListeners = new HashMap<>();
		failListeners = new HashMap<>();
		state = State.RUNNING;
	}

	public synchronized void newScheduled() {
		// We allow tasks to still be scheduled even after failing,
		// they just don't change the state of the operation
		if (state != State.RUNNING && state != State.FAILED) {
			throw new IllegalStateException("TaskOperation has finished already");
		}

		runningCount++;
	}

	public synchronized void oneSucceeded(TaskScheduler scheduler) {
		runningCount--;

		if (state == State.FAILED) {
			return;
		}

		if (runningCount <= 0) {
			succeedNow(scheduler);
		}
	}

	private void succeedNow(TaskScheduler scheduler) {
		state = State.DONE;
		finishListeners.forEach((k, v) -> {
			if (v != null) {
				scheduler.schedule(
						new UniversalRunnableTask("OperationFinishListener", v), false, k);
			} else {
				k.oneSucceeded(scheduler);
			}
		});
		finishListeners.clear();
		finishScheduler = scheduler;
	}

	public synchronized void oneFailed(Throwable t, TaskScheduler scheduler) {
		if (state == State.DONE) {
			throw new IllegalStateException("Can't fail TaskOperation which is already done");
		}

		failNow(t, scheduler);
	}

	private void failNow(Throwable t, TaskScheduler scheduler) {
		state = State.FAILED;

		error = t;
		failListeners.forEach((k, v) -> {
			if (v != null) {
				scheduler.schedule(
						new UniversalRunnableTask("OperationFailListener", () -> v.accept(t)), false, k);
			} else {
				k.oneFailed(t, scheduler);
			}
		});
		failListeners.clear();
		finishScheduler = scheduler;
	}

	public synchronized TaskOperation then(IThrowingRunnable<Throwable> listener) {
		TaskOperation newOperation = new TaskOperation();

		if (state == State.DONE) {
			if (finishScheduler.isShuttingDown()) {
				return newOperation;
			}

			finishScheduler.schedule(new UniversalRunnableTask(
					"OperationFinishListener", listener), false, newOperation);
		} else if (state == State.FAILED) {
			newOperation.failNow(error, finishScheduler);
		} else if (state == State.RUNNING) {
			finishListeners.put(newOperation, listener);
			failListeners.put(newOperation, null);
		}

		return newOperation;
	}

	public synchronized TaskOperation except(IThrowingConsumer<Throwable, Throwable> consumer) {
		TaskOperation newOperation = new TaskOperation();

		if (state == State.FAILED) {
			if (finishScheduler.isShuttingDown()) {
				return newOperation;
			}

			finishScheduler.schedule(new UniversalRunnableTask(
					"OperationFailListener", () -> consumer.accept(error)), false, newOperation);
		} else if (state == State.DONE) {
			newOperation.succeedNow(finishScheduler);
		} else if (state == State.RUNNING) {
			finishListeners.put(newOperation, null);
			failListeners.put(newOperation, consumer);
		}

		return newOperation;
	}

	public synchronized void expandWith(TaskOperation... operations) {
		if (state != State.RUNNING) {
			throw new IllegalStateException("TaskOperation has finished already");
		}

		for (TaskOperation op : operations) {
			//noinspection SynchronizationOnLocalVariableOrMethodParameter
			synchronized (op) {
				if (op.state == State.RUNNING) {
					op.finishListeners.put(this, null);
					op.failListeners.put(this, null);
					runningCount++;
				} else if (op.state == State.FAILED) {
					failNow(op.error, op.finishScheduler);
				}
			}
		}
	}

	public synchronized State state() {
		return state;
	}
}
