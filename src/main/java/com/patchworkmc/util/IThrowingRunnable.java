package com.patchworkmc.util;

@FunctionalInterface
public interface IThrowingRunnable<E extends Throwable> {
	void run() throws E;
}
