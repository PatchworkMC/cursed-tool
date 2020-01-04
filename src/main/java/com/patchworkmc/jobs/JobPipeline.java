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
import java.util.List;

import com.patchworkmc.jobs.meta.JobConnectionMeta;

/**
 * Top level representation of a pipeline.
 */
public class JobPipeline {
	private final List<JobConnectionMeta> jobs;

	public JobPipeline() {
		jobs = new ArrayList<>();
	}

	public void addJob(JobConnectionMeta job) {
		jobs.add(job);
	}
}
