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

package com.patchworkmc.jobs.meta;

import java.util.HashMap;
import java.util.Map;

public class JobInputCreator {
	private final Map<String, InputSetter> inputSetters;
	private Object target;

	public JobInputCreator(Map<String, InputSetter> inputSetters, Object target) {
		this.inputSetters = new HashMap<>(inputSetters);
		this.target = target;
	}

	public boolean requires(String name) {
		return inputSetters.containsKey(name);
	}

	public void set(String name, Object value) {
		if (name.equals("this")) {
			target = cast(inputSetters.get("this").type(), value);
			return;
		}

		InputSetter setter = inputSetters.remove(name);

		try {
			setter.setter().invoke(target, cast(setter.type(), value));
		} catch (Throwable throwable) {
			throw new RuntimeException("BUG: Unexpected error while setting value, fix the validation", throwable);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T cast(Class<T> target, Object value) {
		if (target != value.getClass()) {
			if (Number.class.isAssignableFrom(getNonPrimitive(target))) {
				Number number = (Number) value;

				if (target == byte.class || target == Byte.class) {
					return (T) new Byte(number.byteValue());
				} else if (target == short.class || target == Short.class) {
					return (T) new Short(number.shortValue());
				} else if (target == int.class || target == Integer.class) {
					return (T) new Integer(number.intValue());
				} else if (target == long.class || target == Long.class) {
					return (T) new Long(number.longValue());
				} else if (target == float.class || target == Float.class) {
					return (T) new Float(number.floatValue());
				} else if (target == double.class || target == Double.class) {
					return (T) new Double(number.doubleValue());
				}
			} else {
				return target.cast(value);
			}
		}

		return (T) value;
	}

	private Class<?> getNonPrimitive(Class<?> maybePrimitive) {
		if (!maybePrimitive.isPrimitive()) {
			return maybePrimitive;
		}

		if (maybePrimitive == byte.class) {
			return Byte.class;
		} else if (maybePrimitive == char.class) {
			return Character.class;
		} else if (maybePrimitive == short.class) {
			return Short.class;
		} else if (maybePrimitive == int.class) {
			return Integer.class;
		} else if (maybePrimitive == long.class) {
			return Long.class;
		} else if (maybePrimitive == float.class) {
			return Float.class;
		} else if (maybePrimitive == double.class) {
			return Double.class;
		} else if (maybePrimitive == void.class) {
			return Void.class;
		} else {
			throw new AssertionError("UNREACHABLE");
		}
	}

	public boolean isReady() {
		return inputSetters.size() < 1;
	}

	public Object get() {
		return target;
	}
}
