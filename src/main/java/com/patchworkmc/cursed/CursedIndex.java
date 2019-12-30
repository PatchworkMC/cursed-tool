package com.patchworkmc.cursed;

import java.io.File;
import java.util.List;
import java.util.function.BiConsumer;

import com.patchworkmc.curse.CurseApi;
import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.curse.data.CurseAddonFile;
import com.patchworkmc.cursed.tasks.CursedIndexFileDownloadTask;
import com.patchworkmc.cursed.tasks.CursedIndexFilesFetchTask;
import com.patchworkmc.cursed.tasks.CursedIndexModFetchTask;
import com.patchworkmc.cursed.tasks.CursedIndexInitTask;
import com.patchworkmc.logging.Logger;
import com.patchworkmc.task.TaskOperation;
import com.patchworkmc.task.TaskScheduler;

public class CursedIndex {
	public static final CursedIndex INSTANCE = new CursedIndex();

	private int minecraftGameId = -1;
	private int modsCategoryId = -1;

	private Logger logger;
	private CurseApi curseApi;
	private TaskScheduler scheduler;
	private File indexDir;

	public CurseApi getCurseApi() {
		return curseApi;
	}

	public TaskScheduler getScheduler() {
		return scheduler;
	}

	public void setMinecraftGameId(int minecraftGameId) {
		this.minecraftGameId = minecraftGameId;
	}

	public int minecraftGameId() {
		return minecraftGameId;
	}

	public void setModsCategoryId(int modsCategoryId) {
		this.modsCategoryId = modsCategoryId;
	}

	public int modsCategoryId() {
		return modsCategoryId;
	}

	public void setIndexDir(File indexDir) {
		this.indexDir = indexDir;
	}

	public File indexDir() {
		return indexDir;
	}

	public TaskOperation init(Logger logger, CurseApi curseApi, TaskScheduler scheduler) {
		this.logger = logger;
		this.curseApi = curseApi;
		this.scheduler = scheduler;

		return scheduler.schedule(new CursedIndexInitTask());
	}

	public TaskOperation downloadLatestAddons(int batchSize, int limit, String gameVersion) {
		return scheduler.schedule(
				new CursedIndexModFetchTask(
						batchSize,
						limit,
						gameVersion,
						(addons) -> scheduleFileFetch(addons, gameVersion,
								(addon, files) -> scheduleDownload(addon, files, true))
				)
		);
	}

	public TaskOperation scheduleFileFetch(
			List<CurseAddon> addons,
			String gameVersion,
			BiConsumer<CurseAddon, List<CurseAddonFile>> consumer
	) {
		return scheduler.schedule(new CursedIndexFilesFetchTask(addons, gameVersion, consumer), true);
	}

	public TaskOperation scheduleDownload(
			CurseAddon addon,
			List<CurseAddonFile> files,
			boolean latestOnly
	) {
		return scheduler.schedule(new CursedIndexFileDownloadTask(addon, files, latestOnly), true);
	}
}
