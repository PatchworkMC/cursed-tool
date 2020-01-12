package com.patchworkmc.jobs;

import java.util.List;

public class JobIOType<T> {
	private final Class<?> valueType;
	private final boolean list;

	private JobIOType(Class<?> valueType, boolean list) {
		this.valueType = valueType;
		this.list = list;
	}

	public static <T> JobIOType<T> single(Class<T> valueType) {
		return new JobIOType<>(valueType, false);
	}

	public static <T> JobIOType<List<T>> list(Class<T> valueType) {
		return new JobIOType<>(valueType, true);
	}

	public Class<?> getValueType() {
		return valueType;
	}

	public boolean isList() {
		return list;
	}

	public String getName() {
		return list ? "List of " + valueType.getName() : valueType.getName();
	}

	@SuppressWarnings("unchecked")
	public T cast(Object o) {
		return (T) (isList() ? (List<?>) o : getValueType().cast(o));
	}

	public boolean isCompatible(JobIOType<?> other) {
		return other.isList() == isList() && getValueType().isAssignableFrom(other.getValueType());
	}
}
