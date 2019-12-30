package com.patchworkmc.cursed.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.curse.data.CurseAddonFile;
import com.patchworkmc.cursed.CursedIndex;
import com.patchworkmc.json.JsonConverter;
import com.patchworkmc.json.JsonConverterException;
import com.patchworkmc.logging.Logger;
import com.patchworkmc.task.Task;

public class CursedIndexFileDownloadTask extends Task {
	private final CurseAddon addon;
	private final List<CurseAddonFile> allFiles;
	private final boolean latestOnly;

	private List<CurseAddonFile> targetFiles;
	private boolean addonFileWritten;

	public CursedIndexFileDownloadTask(CurseAddon addon, List<CurseAddonFile> files, boolean latestOnly) {
		this.addon = addon;
		this.allFiles = files;
		this.latestOnly = latestOnly;
	}

	@Override
	protected boolean run(Logger logger) throws Throwable {
		if (targetFiles == null) {
			if (allFiles.isEmpty()) {
				logger.warn("No files to download given");
				return true;
			}

			if (latestOnly) {
				targetFiles = new ArrayList<CurseAddonFile>();
				targetFiles.add(
						allFiles
								.stream()
								.max((Comparator.comparing(CurseAddonFile::getFileDate)))
								.orElse(allFiles.get(0))
				);
			} else {
				targetFiles = new ArrayList<>(allFiles);
			}
		}

		CurseAddonFile currentFile = targetFiles.remove(0);
		File localAddonDir = new File(CursedIndex.INSTANCE.indexDir(), addon.getId() + "-" + addon.getName());

		logger.debug("Running download preparation for file %d (%s)", currentFile.getId(), currentFile.getDisplayName());

		if (!localAddonDir.exists()) {
			logger.trace("Creating local addon dir " + localAddonDir.getPath());

			if (!localAddonDir.mkdirs()) {
				throw new IOException("Failed to create local addon dir " + localAddonDir.getPath());
			}
		}

		File localFile = new File(localAddonDir, currentFile.getFileName());
		File localAddonInfoFile = new File(localAddonDir, "addon.json");
		File infoFile = new File(localFile.getPath() + ".json");

		if (localFile.exists()) {
			CurseAddonFile localFileInfo = getLocalInfo(infoFile);

			if (localFileInfo != null && localFileInfo.getFileDate().compareTo(currentFile.getFileDate()) >= 0) {
				logger.info("Skipping download of %d (%s), local file is up-to-date",
						currentFile.getId(), currentFile.getDisplayName());
				return targetFiles.isEmpty();
			}
		}

		logger.info("Downloading file %d (%s)", currentFile.getId(), currentFile.getDisplayName());

		Files.copy(CursedIndex.INSTANCE.getCurseApi().downloadFile(currentFile), localFile.toPath());

		if (!addonFileWritten) {
			try (FileWriter infoWriter = new FileWriter(localAddonInfoFile)) {
				JsonConverter.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(infoWriter, addon);
			}

			addonFileWritten = true;
		}

		try (FileWriter infoWriter = new FileWriter(infoFile)) {
			JsonConverter.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(infoWriter, currentFile);
		}

		logger.debug("Download finished, remaining files: %d", targetFiles.size());

		return targetFiles.isEmpty();
	}

	private CurseAddonFile getLocalInfo(File infoFile) throws IOException, JsonConverterException {
		if (infoFile.exists()) {
			try (FileInputStream stream = new FileInputStream(infoFile)) {
				return JsonConverter.streamToObject(stream, CurseAddonFile.class);
			}
		}

		return null;
	}

	@Override
	public String name() {
		return "CursedIndexFileDownload";
	}
}
