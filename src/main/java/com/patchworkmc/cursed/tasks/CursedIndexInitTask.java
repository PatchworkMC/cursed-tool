/*
 * Patchwork Project
 * Copyright (C) 2019 PatchworkMC and contributors
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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

/**
 * Root task which initializes the curse index. This includes
 * things such as retrieving required ID's and creating directories.
 */
public class CursedIndexInitTask extends Task {
	@Override
	protected boolean run(Logger logger) throws Throwable {
		// Schedule the fetch of the ID's
		// Note that the schedule expands the current operation since the initialization depends
		// on the ID's, so does this task
		CursedIndex.INSTANCE.scheduler().schedule(new FetchGameIdTask(), true);
		CursedIndex.INSTANCE.scheduler().schedule(new FetchModsCategoryTask(), true);

		// Check if the index directory exists and create it if required
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

	/**
	 * Sub task of {@link CursedIndexInitTask} which fetches the minecraft game id.
	 */
	static class FetchGameIdTask extends Task {
		@Override
		protected boolean run(Logger logger) throws Throwable {
			// Request all games from curse
			List<CurseGame> allGames = CursedIndex.INSTANCE.curseApi().getGames();

			for (CurseGame game : allGames) {
				logger.trace("Found game with id %d named %s", game.getId(), game.getName());

				// Check if the game slug is minecraft, if so, we found it
				if (game.getSlug().equals("minecraft")) {
					logger.info("Found minecraft with game id %d", game.getId());

					// Update the index with the ID
					CursedIndex.INSTANCE.setMinecraftGameId(game.getId());
					return true;
				}
			}

			// WOOOOSH... minecraft disappeared from curse
			throw new NoSuchElementException("Failed to find Minecraft in Curse games");
		}

		@Override
		public String name() {
			return "CursedIndexInit/FetchGameId";
		}
	}

	/**
	 * Sub task of {@link CursedIndexInitTask} which fetches the mods category id.
	 */
	static class FetchModsCategoryTask extends Task {
		@Override
		protected boolean run(Logger logger) throws Throwable {
			// Request all categories from curse
			List<CurseCategory> allCategories = CursedIndex.INSTANCE.curseApi().getCategories();

			for (CurseCategory category : allCategories) {
				logger.trace("Found category with id %d named %s", category.getId(), category.getName());

				// Check if the category slug is mc-mods, if so, we found it
				if (category.getSlug().equals("mc-mods")) {
					logger.info("Found minecraft mods category with id %d", category.getId());

					// Update the index with the ID
					CursedIndex.INSTANCE.setModsCategoryId(category.getId());
					return true;
				}
			}

			// Well, looks like curse does not have minecraft mods anymore, huh?
			// Or they changed the slug...
			throw new NoSuchElementException("Failed to find MinecraftMods category in Curse categories");
		}

		@Override
		public String name() {
			return "CursedIndexInit/FetchModsCategoryId";
		}
	}
}
