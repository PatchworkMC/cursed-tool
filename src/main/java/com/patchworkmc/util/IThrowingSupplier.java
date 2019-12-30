package com.patchworkmc.util;

public interface IThrowingSupplier<T, E extends Throwable> {
	T get() throws E;
}
