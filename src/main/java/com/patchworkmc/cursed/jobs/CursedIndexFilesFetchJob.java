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
