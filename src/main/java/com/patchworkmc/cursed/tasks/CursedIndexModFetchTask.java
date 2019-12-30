package com.patchworkmc.cursed.tasks;

import java.util.List;
import java.util.function.Consumer;

import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.cursed.CursedIndex;
import com.patchworkmc.logging.Logger;
import com.patchworkmc.task.Task;

public class CursedIndexModFetchTask extends Task {
	private final int batchSize;
	private final int limit;
	private final String gameVersion;
	private final Consumer<List<CurseAddon>> addonConsumer;

	private int currentIndex;

	public CursedIndexModFetchTask(
			int batchSize,
			int limit,
			String gameVersion,
			Consumer<List<CurseAddon>> addonConsumer
	) {
		this.batchSize = batchSize;
		this.limit = limit;
		this.gameVersion = gameVersion;
		this.addonConsumer = addonConsumer;
		this.currentIndex = 0;
	}

	@Override
	protected boolean run(Logger logger) throws Throwable {
		logger.info("Fetching next %d mods based on index %d", batchSize, currentIndex);
		CursedIndex cursedIndex = CursedIndex.INSTANCE;

		List<CurseAddon> addons = cursedIndex.getCurseApi().searchAddons(
				cursedIndex.minecraftGameId(),
				gameVersion,
				currentIndex,
				batchSize,
				cursedIndex.modsCategoryId()
		);

		logger.debug("Found %d addons", addons.size());
		currentIndex += batchSize;

		if (limit > 0 && currentIndex > limit) {
			logger.trace("Truncating amount of addons to respect limit");
			addons = addons.subList(0, addons.size() - (currentIndex - limit));
			addonConsumer.accept(addons);
			return true;
		}

		addonConsumer.accept(addons);

		return batchSize > addons.size();
	}

	@Override
	public String name() {
		return "CurseIndexModFetch";
	}
}
