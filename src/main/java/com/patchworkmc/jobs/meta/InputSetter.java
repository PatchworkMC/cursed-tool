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

import com.patchworkmc.jobs.JobIOType;

public class InputSetter {
	private final JobIOType<?> type;
	private final MethodHandle setter;
	private final boolean optional;

	public InputSetter(JobIOType<?> type, MethodHandle setter, boolean optional) {
		this.type = type;
		this.setter = setter;
		this.optional = optional;
	}

	public JobIOType<?> type() {
		return type;
	}

	public MethodHandle setter() {
		return setter;
	}

	public boolean optional() {
		return optional;
	}
}
