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

import java.util.function.Consumer;

/**
 * Representation of a single job in a pipeline.
 *
 * @param <I> The input type of the job
 * @param <O> The output type of the job
 */
public abstract class Job<I, O> {
	private final String namespace;
	private final String name;

	/**
	 * Creates a new Job.
	 *
	 * @param namespace The namespace the job belongs to, for example {@code "cursed-tool"}
	 * @param name      The name of the job, for example {@code "fetch-mods"}
	 */
	public Job(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}

	/**
	 * Schedules a new task which does the work this job should does.
	 *
	 * @param input    The input of the iteration
	 * @param consumer The output consumer of the iteration
	 */
	public abstract void schedule(I input, Consumer<O> consumer);

	public abstract Class<I> inputType();

	public abstract Class<O> outputType();

	public final String namespace() {
		return namespace;
	}

	public final String name() {
		return name;
	}
}
