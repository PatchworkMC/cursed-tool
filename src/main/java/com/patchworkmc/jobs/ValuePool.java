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

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.patchworkmc.jobs.meta.JobInputCreator;
import com.patchworkmc.task.Task;

/**
 * Class for collecting input values for jobs.
 */
public class ValuePool {
	private final Job<Object, Object> job;
	private final Supplier<JobInputCreator> inputCreatorSupplier;
	private final Map<String, ValueConnection> staticConnections;
	private final Queue<JobInputCreator> creators;

	private Consumer<Object> outputConsumer;
	private Consumer<Task> scheduleCallback;

	@SuppressWarnings("unchecked")
	public ValuePool(
			Job<?, ?> job,
			Supplier<JobInputCreator> inputCreatorSupplier,
			Map<String, ValueConnection> staticConnections
	) {
		this.job = (Job<Object, Object>) job;
		this.staticConnections = staticConnections;
		this.inputCreatorSupplier = inputCreatorSupplier;
		this.creators = new LinkedList<>();
	}

	public void supply(String name, Object value) {
		JobInputCreator readyOne = null;

		synchronized (creators) {
			boolean fittingFound = false;

			for (JobInputCreator creator : creators) {
				staticConnections.forEach((connectionName, connection) -> {
					if (creator.requires(connectionName)) {
						creator.set(connectionName, connection.poll());
					}
				});

				if (creator.requires(name)) {
					creator.set(name, value);
					fittingFound = true;

					if (creator.isReady()) {
						readyOne = creator;
					}
				}
			}

			if (!fittingFound) {
				JobInputCreator creator = inputCreatorSupplier.get();
				creator.set(name, value);
				creators.add(creator);
			}

			creators.remove(readyOne);
		}

		if (readyOne != null) {
			Task configured = job.createTask(
					job.inputType().cast(readyOne.get()),
					outputConsumer != null ? outputConsumer : (v) -> {
					});
			scheduleCallback.accept(configured);
		}
	}

	public void pollStatic() {
		if (creators.size() < 1) {
			creators.add(inputCreatorSupplier.get());
		}

		for (JobInputCreator creator : creators) {
			staticConnections.forEach((connectionName, connection) -> {
				if (creator.requires(connectionName)) {
					creator.set(connectionName, connection.poll());
				}

				if (creator.isReady()) {
					Task configured = job.createTask(
							job.inputType().cast(creator.get()),
							outputConsumer != null ? outputConsumer : (v) -> {
							});
					scheduleCallback.accept(configured);
				}
			});
		}
	}

	public void onEmit(Consumer<Object> consumer) {
		this.outputConsumer = consumer;
	}

	public void setScheduleCallback(Consumer<Task> scheduleCallback) {
		this.scheduleCallback = scheduleCallback;
	}
}
