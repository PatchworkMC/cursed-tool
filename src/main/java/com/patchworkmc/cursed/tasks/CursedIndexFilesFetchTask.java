package com.patchworkmc.cursed.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.curse.data.CurseAddonFile;
import com.patchworkmc.cursed.CursedIndex;
import com.patchworkmc.logging.Logger;
import com.patchworkmc.task.Task;

/**
 * Task which fetches information about all files from an addon
 * for a specific game version from curse.
 */
public class CursedIndexFilesFetchTask extends Task {
	// Metadata required to perform the task
	private final List<CurseAddon> addons;
	private final String gameVersion;
	private final BiConsumer<CurseAddon, List<CurseAddonFile>> addonFileConsumer;

	/**
	 * Creates a new {@link CursedIndexFilesFetchTask}.
	 *
	 * @param addons            The addons to fetch file info from
	 * @param gameVersion       The game version to fetch files for
	 * @param addonFileConsumer The callback which should be called when data is available
	 */
	public CursedIndexFilesFetchTask(
			List<CurseAddon> addons,
			String gameVersion,
			BiConsumer<CurseAddon, List<CurseAddonFile>> addonFileConsumer
	) {
		this.addons = new ArrayList<>(addons);
		this.gameVersion = gameVersion;
		this.addonFileConsumer = addonFileConsumer;
	}

	@Override
	protected boolean run(Logger logger) throws Throwable {
		// Remove one addon so we don't process it multiple times
		CurseAddon currentAddon = addons.remove(0);
		logger.debug("Retrieving list of files for addon %d", currentAddon.getId());

		// Request all files for the addon from curse and filter them
		List<CurseAddonFile> files =
				CursedIndex.INSTANCE.curseApi().getAddonFiles(currentAddon.getId())
						.stream()
						// Only fetch files which match the game version
						.filter(f -> f.getGameVersion().contains(gameVersion))
						.collect(Collectors.toList());

		// Inform the callback we found files
		logger.info("Found %d files for addon %d", files.size(), currentAddon.getId());
		addonFileConsumer.accept(currentAddon, files);

		// The task is done if addons is empty
		logger.trace("Done processing one addon, %d left", addons.size());
		return addons.isEmpty();
	}

	@Override
	public String name() {
		return "CursedIndexFetch";
	}
}
