package com.patchworkmc.util;

public interface IThrowingConsumer<T, E extends Throwable> {
	void accept(T t) throws E;
}
