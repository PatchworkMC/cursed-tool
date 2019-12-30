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

public class CursedIndexFilesFetchTask extends Task {
	private final List<CurseAddon> addons;
	private final String gameVersion;
	private final BiConsumer<CurseAddon, List<CurseAddonFile>> addonFileConsumer;

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
		if (addons.isEmpty()) {
			return true;
		}

		CurseAddon currentAddon = addons.remove(0);
		logger.debug("Retrieving list of files for addon %d", currentAddon.getId());

		List<CurseAddonFile> files =
				CursedIndex.INSTANCE.getCurseApi().getAddonFiles(currentAddon.getId())
						.stream()
						.filter(f -> f.getGameVersion().contains(gameVersion))
						.collect(Collectors.toList());
		logger.info("Found %d files for addon %d", files.size(), currentAddon.getId());
		addonFileConsumer.accept(currentAddon, files);

		logger.trace("Done processing one addon, %d left", addons.size());
		return addons.isEmpty();
	}

	@Override
	public String name() {
		return "CursedIndexFetch";
	}
}
