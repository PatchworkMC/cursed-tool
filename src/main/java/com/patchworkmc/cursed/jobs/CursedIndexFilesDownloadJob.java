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

import java.util.function.Consumer;

import com.patchworkmc.cursed.CurseAddonFilesMeta;
import com.patchworkmc.cursed.tasks.CursedIndexFileDownloadTask;
import com.patchworkmc.jobs.Job;
import com.patchworkmc.jobs.JobIOType;
import com.patchworkmc.jobs.meta.OptionalInput;
import com.patchworkmc.task.Task;

public class CursedIndexFilesDownloadJob extends Job<CursedIndexFilesDownloadJob.Input, Void> {
	public CursedIndexFilesDownloadJob() {
		super("cursed-tool", "files-download");
	}

	public static class Input {
		public CurseAddonFilesMeta addonFiles;

		@OptionalInput
		public boolean latestOnly = true;
	}

	@Override
	public Task createTask(Input input, Consumer<Void> consumer) {
		return new CursedIndexFileDownloadTask(input.addonFiles.addon, input.addonFiles.files, input.latestOnly);
	}

	@Override
	public Class<Input> inputType() {
		return Input.class;
	}

	@Override
	public JobIOType<Void> outputType() {
		return JobIOType.single(Void.class);
	}
}
