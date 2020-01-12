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
import com.patchworkmc.cursed.CurseAddonFilesMeta;
import com.patchworkmc.cursed.tasks.CursedIndexFilesFetchTask;
import com.patchworkmc.jobs.Job;
import com.patchworkmc.jobs.JobIOType;
import com.patchworkmc.task.Task;

public class CursedIndexFilesFetchJob extends Job<CursedIndexFilesFetchJob.Input, CurseAddonFilesMeta> {
	public CursedIndexFilesFetchJob() {
		super("cursed-tool", "files-fetch");
	}

	public static class Input {
		public List<CurseAddon> addons;

		public String gameVersion;
	}

	@Override
	public Task createTask(Input input, Consumer<CurseAddonFilesMeta> consumer) {
		return new CursedIndexFilesFetchTask(input.addons, input.gameVersion,
				(addon, files) -> consumer.accept(new CurseAddonFilesMeta(addon, files)));
	}

	@Override
	public Class<Input> inputType() {
		return Input.class;
	}

	@Override
	public JobIOType<CurseAddonFilesMeta> outputType() {
		return JobIOType.single(CurseAddonFilesMeta.class);
	}
}
