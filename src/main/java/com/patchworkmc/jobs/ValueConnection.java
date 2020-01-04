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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.patchworkmc.jobs.parser.token.JobDefinitionToken;

/**
 * Represents a connection between a job output or constant and input.
 */
public class ValueConnection {
	private final Class<?> valueType;
	private final JobDefinitionToken origin;
	private final List<JobDefinitionToken> usages;
	private final Set<Consumer<Object>> consumers;
	private final Supplier<Object> staticSupplier;

	public ValueConnection(Class<?> valueType, JobDefinitionToken origin) {
		this.valueType = valueType;
		this.origin = origin;
		this.usages = new ArrayList<>();
		this.consumers = new HashSet<>();
		this.staticSupplier = null;
	}

	@SuppressWarnings("unchecked")
	public <T> ValueConnection(Class<T> valueType, JobDefinitionToken origin, Supplier<T> staticSupplier) {
		this.valueType = valueType;
		this.origin = origin;
		this.usages = Collections.singletonList(origin);
		this.consumers = new HashSet<>();
		this.staticSupplier = (Supplier<Object>) staticSupplier;
	}

	public void supply(Object o) {
		consumers.forEach(c -> c.accept(valueType.cast(o)));
	}

	@SuppressWarnings("unchecked")
	public void addConsumer(Consumer<?> consumer) {
		consumers.add((Consumer<Object>) consumer);
	}

	public Class<?> getValueType() {
		return valueType;
	}

	public Object poll() {
		if (staticSupplier != null) {
			return staticSupplier.get();
		} else {
			return null;
		}
	}

	public boolean isStatic() {
		return staticSupplier != null;
	}

	public JobDefinitionToken getOrigin() {
		return origin;
	}

	public List<JobDefinitionToken> getUsages() {
		return usages;
	}
}
