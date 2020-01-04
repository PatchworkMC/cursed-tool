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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.patchworkmc.jobs.Job;
import com.patchworkmc.jobs.ValueConnection;

public class JobMeta {
	// TODO: This class contains a lot of reflection operation, once in
	//		 application-core, this should go into its own package

	private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
	private static final List<Class<?>> SIMPLE_TYPES = Arrays.asList(
			boolean.class, Boolean.class,
			byte.class, Byte.class,
			char.class, Character.class,
			short.class, Short.class,
			int.class, Integer.class,
			long.class, Long.class,
			float.class, Float.class,
			double.class, Double.class,
			void.class, Void.class,
			String.class
	);

	private final Job<?, ?> job;
	private final Class<?> inputType;
	private final Map<String, InputSetter> inputs;

	MethodHandle constructor;

	public JobMeta(Job<?, ?> job) {
		this.job = job;
		this.inputType = job.inputType();
		this.inputs = new HashMap<>();
	}

	public void setup() throws MetaBindException {
		boolean entireInputOptional;

		try {
			Method m = job.getClass().getMethod("inputType");
			entireInputOptional = m.getDeclaredAnnotation(OptionalInput.class) != null;
		} catch (NoSuchMethodException e) {
			throw new AssertionError("UNREACHABLE", e);
		}

		if (SIMPLE_TYPES.contains(inputType)) {
			inputs.put("this", new InputSetter(inputType, null, entireInputOptional));
			return;
		}

		try {
			constructor = LOOKUP.findConstructor(inputType, MethodType.methodType(void.class));
		} catch (NoSuchMethodException e) {
			throw new MetaBindException(
					"Input type " + inputType.getName() + " does not provide a no args constructor", e);
		} catch (IllegalAccessException e) {
			throw new MetaBindException("Failed to bind no args constructor on " + inputType.getName(), e);
		}

		try {
			for (Field f : inputType.getDeclaredFields()) {
				inputs.put(f.getName(), new InputSetter(f.getType(), LOOKUP.unreflectSetter(f),
						f.getDeclaredAnnotation(OptionalInput.class) != null || entireInputOptional));
			}
		} catch (IllegalAccessException e) {
			throw new MetaBindException("Failed to bind setters on " + inputType.getName(), e);
		}
	}

	private Object createInputTarget() {
		if (SIMPLE_TYPES.contains(inputType)) {
			if (inputType == boolean.class || inputType == Boolean.class) {
				return OptionalInput.DEFAULT_BOOLEAN;
			} else if (inputType == byte.class || inputType == Byte.class) {
				return OptionalInput.DEFAULT_BYTE;
			} else if (inputType == char.class || inputType == Character.class) {
				return OptionalInput.DEFAULT_CHAR;
			} else if (inputType == short.class || inputType == Short.class) {
				return OptionalInput.DEFAULT_SHORT;
			} else if (inputType == int.class || inputType == Integer.class) {
				return OptionalInput.DEFAULT_INT;
			} else if (inputType == long.class || inputType == Long.class) {
				return OptionalInput.DEFAULT_LONG;
			} else if (inputType == void.class || inputType == Void.class) {
				return OptionalInput.DEFAULT_VOID;
			} else if (inputType == String.class) {
				return OptionalInput.DEFAULT_STRING;
			} else {
				throw new AssertionError("UNREACHABLE");
			}
		}

		try {
			return constructor.invoke();
		} catch (Throwable t) {
			throw new RuntimeException("Constructing input " + inputType.getName() + " threw an exception", t);
		}
	}

	public Supplier<JobInputCreator> makeCreatorSupplier(Map<String, ValueConnection> connections)
			throws MetaBindException {
		Map<String, InputSetter> requiredInputs = new HashMap<>(inputs);
		Map<String, InputSetter> presentInputs = new HashMap<>();

		for (Map.Entry<String, ValueConnection> connection : connections.entrySet()) {
			if (!requiredInputs.containsKey(connection.getKey())) {
				throw new MetaBindException("Input " + connection.getKey() + " does not exist on job " + job.name());
			}

			presentInputs.put(connection.getKey(), requiredInputs.get(connection.getKey()));
			requiredInputs.remove(connection.getKey());
		}

		for (Map.Entry<String, InputSetter> leftover : requiredInputs.entrySet()) {
			if (!leftover.getValue().optional()) {
				throw new MetaBindException("Required input " + leftover.getKey() + " not bound");
			}
		}

		return () -> new JobInputCreator(presentInputs, createInputTarget());
	}

	public Job<?, ?> job() {
		return job;
	}
}
