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

package com.patchworkmc.cursed;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.patchworkmc.curse.CurseApi;
import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.curse.data.CurseAddonFile;
import com.patchworkmc.cursed.tasks.CursedIndexFileDownloadTask;
import com.patchworkmc.cursed.tasks.CursedIndexFilesFetchTask;
import com.patchworkmc.cursed.tasks.CursedIndexModFetchTask;
import com.patchworkmc.cursed.tasks.CursedIndexInitTask;
import com.patchworkmc.logging.Logger;
import com.patchworkmc.task.TaskScheduler;
import com.patchworkmc.task.TaskTracker;

/**
 * Class representing a file structure on disk called the CursedIndex.
 * Yes, Cursed!
 */
public class CursedIndex {
	/**
	 * Instance of the cursed index. This class is a singleton with deferred
	 * initialization.
	 */
	public static final CursedIndex INSTANCE = new CursedIndex();

	// ID's we resolve from curse, they are required to perform further tasks
	private int minecraftGameId = -1;
	private int modsCategoryId = -1;

	// Components required for operation
	private Logger logger;
	private CurseApi curseApi;
	private TaskScheduler scheduler;
	private File indexDir;

	// Make this constructor private since this class
	// is a Singleton
	private CursedIndex() {
	}

	/**
	 * Getter for the CurseApi.
	 *
	 * @return The {@link CurseApi} used by this index
	 */
	public CurseApi curseApi() {
		return curseApi;
	}

	/**
	 * Getter for the scheduler.
	 *
	 * @return The {@link TaskScheduler} used by this index
	 */
	public TaskScheduler scheduler() {
		return scheduler;
	}

	/**
	 * Overwrites the game id for minecraft. This is usually done
	 * by the {@link CursedIndexInitTask}.
	 *
	 * @param minecraftGameId The game id of minecraft
	 */
	public void setMinecraftGameId(int minecraftGameId) {
		this.minecraftGameId = minecraftGameId;
	}

	/**
	 * Getter for the game id.
	 *
	 * @return The curse game id for minecraft
	 */
	public int minecraftGameId() {
		return minecraftGameId;
	}

	/**
	 * Overwrites the mods category id for mc-mods. This is usually
	 * done by the {@link CursedIndexInitTask}.
	 *
	 * @param modsCategoryId The mc-mods category id
	 */
	public void setModsCategoryId(int modsCategoryId) {
		this.modsCategoryId = modsCategoryId;
	}

	/**
	 * Getter for the mc-mods category id.
	 *
	 * @return The curse category id for mc-mods
	 */
	public int modsCategoryId() {
		return modsCategoryId;
	}

	/**
	 * Overwrites the index directory. Needs to be called
	 * before using {@link CursedIndex#init(Logger, CurseApi, TaskScheduler)}.
	 *
	 * @param indexDir The new index directory.
	 */
	public void setIndexDir(File indexDir) {
		this.indexDir = indexDir;
	}

	/**
	 * Getter for the index directory.
	 *
	 * @return The directory this index is located at.
	 */
	public File indexDir() {
		return indexDir;
	}

	/**
	 * Schedules this index to be initialized.
	 *
	 * @param logger    The logger to use for logging events in this index
	 * @param curseApi  The curse api backing this index
	 * @param scheduler The scheduler to schedule tasks on when required
	 * @return A task tracker which can be used for tracking when the init task is done
	 */
	public TaskTracker init(Logger logger, CurseApi curseApi, TaskScheduler scheduler) {
		this.logger = logger;
		this.curseApi = curseApi;
		this.scheduler = scheduler;

		logger.debug("Scheduling initialization task");

		// Schedule the initialization task
		return scheduler.schedule(new CursedIndexInitTask());
	}

	/**
	 * Schedules the downloads of all latest addon files of the specified game version.
	 *
	 * @param batchSize   Amount of mods to request at once. A lower number performs more requests,
	 *                    a higher number takes more time to parse the requests
	 * @param limit       Maximum amount of mods to download
	 * @param gameVersion The game version to download mods for
	 * @return A task tracker which can be used for tracking when all downloads finished
	 */
	public TaskTracker downloadLatestAddons(int batchSize, int limit, String gameVersion) {
		AtomicReference<TaskTracker> downloadTaskTracker = new AtomicReference<>();

		// Callback when addon files have been fetched
		BiConsumer<CurseAddon, List<CurseAddonFile>> onAddonFilesAvailable = (addon, files) -> {
			// As soon as we have have files, schedule them to be downloaded
			downloadTaskTracker.get().track(scheduleDownload(addon, files, true));
		};

		// Callback when addons have been fetched
		Consumer<List<CurseAddon>> onAddonsAvailable = (addons) -> {
			// As soon as we have addons available, fetch their files
			downloadTaskTracker.get().track(scheduleFileFetch(addons, gameVersion, onAddonFilesAvailable));
		};

		TaskTracker taskTracker = scheduler.schedule(
				new CursedIndexModFetchTask(
						batchSize,
						limit,
						gameVersion,
						onAddonsAvailable
				)
		);
		downloadTaskTracker.set(taskTracker);
		return taskTracker;
	}

	/**
	 * Schedules the fetch operation of all files of given addons.
	 *
	 * @param addons      The addons to retrieve the files for
	 * @param gameVersion The game version the files have to be for
	 * @param consumer    A function accepting date generated by this task. Will be called
	 *                    multiple times synchronously. To process the data further, it is
	 *                    recommended to use this callback as an entry point for scheduling
	 *                    other tasks.
	 * @return A task tracker which can be used for tracking when all files have been fetched
	 */
	public TaskTracker scheduleFileFetch(
			List<CurseAddon> addons,
			String gameVersion,
			BiConsumer<CurseAddon, List<CurseAddonFile>> consumer
	) {
		return scheduler.schedule(new CursedIndexFilesFetchTask(addons, gameVersion, consumer));
	}

	/**
	 * Schedules the download of the given files of the given addon.
	 *
	 * @param addon      The addon the files are from
	 * @param files      The files to download. It is assumed that this list only contains
	 *                   files from the addon given by the parameter addon
	 * @param latestOnly Wether only the latest file should be downloaded
	 * @return A task tracker which can be used for tracking when all files have been downloaded
	 */
	public TaskTracker scheduleDownload(
			CurseAddon addon,
			List<CurseAddonFile> files,
			boolean latestOnly
	) {
		return scheduler.schedule(new CursedIndexFileDownloadTask(addon, files, latestOnly));
	}
}
