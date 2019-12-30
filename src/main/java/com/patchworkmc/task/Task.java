package com.patchworkmc.task;

import java.util.ArrayList;
import java.util.List;

import com.patchworkmc.logging.Logger;

public abstract class Task {
	protected abstract boolean run(Logger logger) throws Throwable;

	public abstract String name();

	private final List<Task> dependencies;

	private boolean done;
	private boolean running;
	private Throwable error;
	private boolean canceled;

	protected Task() {
		dependencies = new ArrayList<>();
	}

	final void step(Logger logger) throws Throwable {
		running = true;

		try {
			done = run(logger.sub("Task(" + name() + ")"));
		} catch (Throwable t) {
			running = false;
			error = t;
			done = true;
			throw t;
		}

		running = false;
	}

	public final boolean isDone() {
		return done;
	}

	public final boolean canRun() {
		return dependencies.stream().allMatch(Task::isDone);
	}

	public final void addDependency(Task task) {
		dependencies.add(task);
	}

	public final boolean isRunning() {
		return running;
	}

	public Throwable getError() {
		return error;
	}

	public final void cancel() {
		canceled = true;
	}

	public final boolean isCanceled() {
		return canceled;
	}
}
