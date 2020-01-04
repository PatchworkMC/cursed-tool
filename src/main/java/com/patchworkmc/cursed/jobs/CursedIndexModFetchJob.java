package com.patchworkmc.cursed.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.cursed.tasks.CursedIndexModFetchTask;
import com.patchworkmc.jobs.Job;
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

	@SuppressWarnings("unchecked")
	@Override
	public Class<List<CurseAddon>> outputType() {
		return (Class<List<CurseAddon>>) ((Object) new ArrayList<CurseAddon>()).getClass();
	}
}
