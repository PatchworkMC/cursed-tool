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
