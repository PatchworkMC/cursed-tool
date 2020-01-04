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

import com.patchworkmc.jobs.Job;

public class JobRegistry {
	private final Map<String, JobMeta> registeredJobs;

	public JobRegistry() {
		this.registeredJobs = new HashMap<>();
	}

	public void register(Job<?, ?> job) throws MetaBindException {
		JobMeta meta = new JobMeta(job);
		meta.setup();
		registeredJobs.put(job.namespace() + ":" + job.name(), meta);
	}

	public boolean isRegistered(String qualifiedName) {
		return registeredJobs.containsKey(qualifiedName);
	}

	public JobMeta get(String qualifiedName) {
		return registeredJobs.get(qualifiedName);
	}
}
