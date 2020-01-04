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
			target = inputSetters.get("this").type().cast(value);
			return;
		}

		InputSetter setter = inputSetters.remove(name);

		try {
			setter.setter().invoke(target, value);
		} catch (Throwable throwable) {
			throw new RuntimeException("BUG: Unexpected error while setting value, fix the validation", throwable);
		}
	}

	public boolean isReady() {
		return inputSetters.size() < 1;
	}

	public Object get() {
		return target;
	}
}
