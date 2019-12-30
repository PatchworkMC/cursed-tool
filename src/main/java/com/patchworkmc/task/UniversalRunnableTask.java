package com.patchworkmc.task;

import com.patchworkmc.logging.Logger;
import com.patchworkmc.util.IThrowingRunnable;

public class UniversalRunnableTask extends Task {
	private final String name;
	private final IThrowingRunnable runnable;

	public UniversalRunnableTask(String name, IThrowingRunnable runnable) {
		this.name = name;
		this.runnable = runnable;
	}

	@Override
	protected boolean run(Logger logger) throws Throwable {
		logger.debug("Running universal runnable.");
		runnable.run();
		return true;
	}

	@Override
	public String name() {
		return name;
	}
}
