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

package com.patchworkmc.cursed.tasks;

import java.util.List;
import java.util.function.Consumer;

import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.cursed.CursedIndex;
import com.patchworkmc.logging.Logger;
import com.patchworkmc.task.Task;

/**
 * Task which sequentially fetches all addons from curse.
 */
public class CursedIndexModFetchTask extends Task {
	// Metadata required to perform the task
	private final int batchSize;
	private final int limit;
	private final String gameVersion;
	private final Consumer<List<CurseAddon>> addonConsumer;

	// The index we are at
	private int currentIndex;

	/**
	 * Creates a new {@link CursedIndexModFetchTask}.
	 *
	 * @param batchSize     Amount of mods to request from curse at once
	 * @param limit         Maximum amount of mods to fetch
	 * @param gameVersion   The game version the mods should be of
	 * @param addonConsumer Callback which should be called when data is available
	 */
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

		// Request all addons from curse
		List<CurseAddon> addons = cursedIndex.curseApi().searchAddons(
				cursedIndex.minecraftGameId(),
				gameVersion,
				currentIndex,
				batchSize,
				cursedIndex.modsCategoryId()
		);

		logger.debug("Found %d addons", addons.size());

		// Check if we are exceeding the limit
		// The first idea was to simply limit this by lowering
		// the batch size in the request, but sometimes curse returns too many addons...
		if (limit > 0 && currentIndex + addons.size() > limit) {
			logger.trace("Truncating amount of addons to respect limit");

			// Truncate the list to stay within the bounds and then send it
			addons = addons.subList(0, addons.size() - ((addons.size() + currentIndex) - limit));
			addonConsumer.accept(addons);
			return true;
		}

		// Increment the index by batch size and send the data
		currentIndex += batchSize;
		addonConsumer.accept(addons);

		// The task is done when addons is empty
		return batchSize > addons.size();
	}

	@Override
	public String name() {
		return "CurseIndexModFetch";
	}
}
