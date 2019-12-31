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

package com.patchworkmc.curse.data;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseGame{

	@JsonProperty("fileParsingRules")
	private List<Object> fileParsingRules;

	@JsonProperty("gameFiles")
	private List<CurseSlimGameFile> gameFiles;

	@JsonProperty("supportsPartnerAddons")
	private boolean supportsPartnerAddons;

	@JsonProperty("addonSettingsStartingFolder")
	private Object addonSettingsStartingFolder;

	@JsonProperty("profilerAddonId")
	private int profilerAddonId;

	@JsonProperty("dateModified")
	private Date dateModified;

	@JsonProperty("categorySections")
	private List<CurseCategorySection> categorySections;

	@JsonProperty("maxFileSize")
	private int maxFileSize;

	@JsonProperty("supportsNotifications")
	private boolean supportsNotifications;

	@JsonProperty("addonSettingsFileFilter")
	private Object addonSettingsFileFilter;

	@JsonProperty("addonSettingsFileRemovalFilter")
	private Object addonSettingsFileRemovalFilter;

	@JsonProperty("maxPremiumStorage")
	private int maxPremiumStorage;

	@JsonProperty("supportedClientConfiguration")
	private int supportedClientConfiguration;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	@JsonProperty("gameDetectionHints")
	private List<CurseGameDetectionHint> gameDetectionHints;

	@JsonProperty("supportsAddons")
	private boolean supportsAddons;

	@JsonProperty("clientGameSettingsId")
	private int clientGameSettingsId;

	@JsonProperty("maxFreeStorage")
	private int maxFreeStorage;

	@JsonProperty("addonSettingsFolderFilter")
	private Object addonSettingsFolderFilter;

	@JsonProperty("slug")
	private String slug;

	@JsonProperty("twitchGameId")
	private int twitchGameId;

	public List<Object> getFileParsingRules(){
		return fileParsingRules;
	}

	public List<CurseSlimGameFile> getGameFiles(){
		return gameFiles;
	}

	public boolean isSupportsPartnerAddons(){
		return supportsPartnerAddons;
	}

	public Object getAddonSettingsStartingFolder(){
		return addonSettingsStartingFolder;
	}

	public int getProfilerAddonId(){
		return profilerAddonId;
	}

	public Date getDateModified(){
		return dateModified;
	}

	public List<CurseCategorySection> getCategorySections(){
		return categorySections;
	}

	public int getMaxFileSize(){
		return maxFileSize;
	}

	public boolean isSupportsNotifications(){
		return supportsNotifications;
	}

	public Object getAddonSettingsFileFilter(){
		return addonSettingsFileFilter;
	}

	public Object getAddonSettingsFileRemovalFilter(){
		return addonSettingsFileRemovalFilter;
	}

	public int getMaxPremiumStorage(){
		return maxPremiumStorage;
	}

	public int getSupportedClientConfiguration(){
		return supportedClientConfiguration;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public List<CurseGameDetectionHint> getGameDetectionHints(){
		return gameDetectionHints;
	}

	public boolean isSupportsAddons(){
		return supportsAddons;
	}

	public int getClientGameSettingsId(){
		return clientGameSettingsId;
	}

	public int getMaxFreeStorage(){
		return maxFreeStorage;
	}

	public Object getAddonSettingsFolderFilter(){
		return addonSettingsFolderFilter;
	}

	public String getSlug(){
		return slug;
	}

	public int getTwitchGameId(){
		return twitchGameId;
	}

	@Override
 	public String toString(){
		return 
			"CurseGame{" + 
			"fileParsingRules = '" + fileParsingRules + '\'' + 
			",gameFiles = '" + gameFiles + '\'' + 
			",supportsPartnerAddons = '" + supportsPartnerAddons + '\'' + 
			",addonSettingsStartingFolder = '" + addonSettingsStartingFolder + '\'' + 
			",profilerAddonId = '" + profilerAddonId + '\'' + 
			",dateModified = '" + dateModified + '\'' + 
			",categorySections = '" + categorySections + '\'' + 
			",maxFileSize = '" + maxFileSize + '\'' + 
			",supportsNotifications = '" + supportsNotifications + '\'' + 
			",addonSettingsFileFilter = '" + addonSettingsFileFilter + '\'' + 
			",addonSettingsFileRemovalFilter = '" + addonSettingsFileRemovalFilter + '\'' + 
			",maxPremiumStorage = '" + maxPremiumStorage + '\'' + 
			",supportedClientConfiguration = '" + supportedClientConfiguration + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",gameDetectionHints = '" + gameDetectionHints + '\'' + 
			",supportsAddons = '" + supportsAddons + '\'' + 
			",clientGameSettingsId = '" + clientGameSettingsId + '\'' + 
			",maxFreeStorage = '" + maxFreeStorage + '\'' + 
			",addonSettingsFolderFilter = '" + addonSettingsFolderFilter + '\'' + 
			",slug = '" + slug + '\'' + 
			",twitchGameId = '" + twitchGameId + '\'' + 
			"}";
		}
}