package com.patchworkmc.cursed;

import java.util.List;

import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.curse.data.CurseAddonFile;

public class CurseAddonFilesMeta {
	public final CurseAddon addon;
	public final List<CurseAddonFile> files;

	public CurseAddonFilesMeta(CurseAddon addon, List<CurseAddonFile> files) {
		this.addon = addon;
		this.files = files;
	}
}
