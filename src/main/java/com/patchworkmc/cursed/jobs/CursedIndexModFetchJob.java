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

package com.patchworkmc.cursed.jobs;

import java.util.List;
import java.util.function.Consumer;

import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.cursed.tasks.CursedIndexModFetchTask;
import com.patchworkmc.jobs.Job;
import com.patchworkmc.jobs.JobIOType;
import com.patchworkmc.jobs.meta.OptionalInput;
import com.patchworkmc.task.Task;

public class CursedIndexModFetchJob extends Job<CursedIndexModFetchJob.Input, List<CurseAddon>> {
	public CursedIndexModFetchJob() {
		super("cursed-tool", "mod-fetch");
	}

	public static class Input {
		@OptionalInput
		public int batchSize = 200;

		@OptionalInput
		public int limit = 0;

		public String gameVersion;
	}

	@Override
	public Task createTask(Input input, Consumer<List<CurseAddon>> consumer) {
		return new CursedIndexModFetchTask(input.batchSize, input.limit, input.gameVersion, consumer);
	}

	@Override
	public Class<Input> inputType() {
		return Input.class;
	}

	@Override
	public JobIOType<List<CurseAddon>> outputType() {
		return JobIOType.list(CurseAddon.class);
	}
}
