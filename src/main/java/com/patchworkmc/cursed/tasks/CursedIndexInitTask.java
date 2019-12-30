package com.patchworkmc.cursed.tasks;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.patchworkmc.curse.data.CurseCategory;
import com.patchworkmc.curse.data.CurseGame;
import com.patchworkmc.cursed.CursedIndex;
import com.patchworkmc.logging.Logger;
import com.patchworkmc.task.Task;

public class CursedIndexInitTask extends Task {
	@Override
	protected boolean run(Logger logger) throws Throwable {
		CursedIndex.INSTANCE.getScheduler().schedule(new FetchGameIdTask(), true);
		CursedIndex.INSTANCE.getScheduler().schedule(new FetchModsCategoryTask(), true);

		File indexDir = CursedIndex.INSTANCE.indexDir();

		if (!indexDir.exists()) {
			if (!indexDir.mkdirs()) {
				throw new IOException("Failed to create directory " + indexDir.getPath());
			}
		}

		return true;
	}

	@Override
	public String name() {
		return "CursedIndexInit";
	}

	static class FetchGameIdTask extends Task {
		@Override
		protected boolean run(Logger logger) throws Throwable {
			List<CurseGame> allGames = CursedIndex.INSTANCE.getCurseApi().getGames();

			for (CurseGame game : allGames) {
				logger.trace("Found game with id %d named %s", game.getId(), game.getName());

				if (game.getSlug().equals("minecraft")) {
					logger.info("Found minecraft with game id %d", game.getId());
					CursedIndex.INSTANCE.setMinecraftGameId(game.getId());
					return true;
				}
			}

			throw new NoSuchElementException("Failed to find Minecraft in Curse games");
		}

		@Override
		public String name() {
			return "CursedIndexInit/FetchGameId";
		}
	}

	static class FetchModsCategoryTask extends Task {
		@Override
		protected boolean run(Logger logger) throws Throwable {
			List<CurseCategory> allCategories = CursedIndex.INSTANCE.getCurseApi().getCategories();

			for (CurseCategory category : allCategories) {
				logger.trace("Found category with id %d named %s", category.getId(), category.getName());

				if (category.getSlug().equals("mc-mods")) {
					logger.info("Found minecraft mods category with id %d", category.getId());
					CursedIndex.INSTANCE.setModsCategoryId(category.getId());
					return true;
				}
			}

			throw new NoSuchElementException("Failed to find MinecraftMods category in Curse categories");
		}

		@Override
		public String name() {
			return "CursedIndexInit/FetchModsCategoryId";
		}
	}
}
