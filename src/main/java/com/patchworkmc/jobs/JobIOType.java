/*
 * Patchwork Project
 * Copyright (C) 2019 PatchworkMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
