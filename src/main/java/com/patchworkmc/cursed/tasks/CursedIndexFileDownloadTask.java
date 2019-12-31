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

/**
 * Task which downloads files into the index from curse.
 */
public class CursedIndexFileDownloadTask extends Task {
	// Metadata required for performing the task
	private final CurseAddon addon;
	private final List<CurseAddonFile> allFiles;
	private final boolean latestOnly;

	// Data which changes while the task is running
	private List<CurseAddonFile> targetFiles;
	private boolean addonInfoFileWritten;

	/**
	 * Creates a new {@link CursedIndexFileDownloadTask}.
	 *
	 * @param addon      The addon to fetch the files from
	 * @param files      The files to download. It is required that all files
	 *                   in this List are from the addon given
	 * @param latestOnly Wether only the latest file should be downloaded
	 */
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
				// Collect the latest file
				targetFiles = new ArrayList<>();
				targetFiles.add(
						allFiles
								.stream()
								.max((Comparator.comparing(CurseAddonFile::getFileDate))) // Compare the dates
								.get()
				);
			} else {
				// If we should download all files, just copy over the list
				targetFiles = new ArrayList<>(allFiles);
			}
		}

		// Remove the file so we don't process it again
		CurseAddonFile currentFile = targetFiles.remove(0);
		File localAddonDir = new File(CursedIndex.INSTANCE.indexDir(), addon.getId() + "-" + addon.getName());

		logger.debug("Running download preparation for file %d (%s)", currentFile.getId(), currentFile.getDisplayName());

		// Make sure the local addon dir does exist
		if (!localAddonDir.exists()) {
			logger.trace("Creating local addon dir " + localAddonDir.getPath());

			if (!localAddonDir.mkdirs()) {
				throw new IOException("Failed to create local addon dir " + localAddonDir.getPath());
			}
		}

		/*
		 * All files that may be written:
		 * - localFile: The addon file itself
		 * - localAddonInfoFile: Contains the metadata about the addon
		 * - infoFile: Contains the metadata about the addon file
		 */
		File localFile = new File(localAddonDir, currentFile.getFileName());
		File localAddonInfoFile = new File(localAddonDir, "addon.json");
		File infoFile = new File(localFile.getPath() + ".json");

		// Check if the local file exists already
		if (localFile.exists()) {
			// Retrieve the local metadata
			CurseAddonFile localFileInfo = getLocalInfo(infoFile);

			// If the metadata is present and the local file is newer or as new as the
			// one targetted to download, keep the local one.
			if (localFileInfo != null && localFileInfo.getFileDate().compareTo(currentFile.getFileDate()) >= 0) {
				logger.info("Skipping download of %d (%s), local file is up-to-date",
						currentFile.getId(), currentFile.getDisplayName());

				// The task is done when no more target files are present
				return targetFiles.isEmpty();
			}
		}

		// Perform the download of the addon file itself
		logger.info("Downloading file %d (%s)", currentFile.getId(), currentFile.getDisplayName());
		Files.copy(CursedIndex.INSTANCE.curseApi().downloadFile(currentFile), localFile.toPath());

		if (!addonInfoFileWritten) {
			// If we haven't written the addon info already, do it now
			// since it may update from time to time, we do not check if
			// the file exists but straight overwrite it
			try (FileWriter infoWriter = new FileWriter(localAddonInfoFile)) {
				JsonConverter.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(infoWriter, addon);
			}

			// This only needs to be done once per task
			addonInfoFileWritten = true;
		}

		// Update the local file meta
		try (FileWriter infoWriter = new FileWriter(infoFile)) {
			JsonConverter.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(infoWriter, currentFile);
		}

		logger.debug("Download finished, remaining files: %d", targetFiles.size());

		// The task is done when no more target files are present
		return targetFiles.isEmpty();
	}

	/**
	 * Parses a local info file.
	 *
	 * @param infoFile The file to parse
	 * @return The parsed object or null if the file does not exist
	 * @throws IOException            If an error occurred while reading from the file
	 * @throws JsonConverterException If the file does not contain valid json or does not
	 *                                match the scheme of a {@link CurseAddonFile}
	 */
	private CurseAddonFile getLocalInfo(File infoFile) throws IOException, JsonConverterException {
		if (infoFile.exists()) {
			try (FileInputStream stream = new FileInputStream(infoFile)) {
				// Convert the file stream to a CurseAddonFile
				return JsonConverter.streamToObject(stream, CurseAddonFile.class);
			}
		}

		// The file does not exist so we have no local meta
		return null;
	}

	@Override
	public String name() {
		return "CursedIndexFileDownload";
	}
}
