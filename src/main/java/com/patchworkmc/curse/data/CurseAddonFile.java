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

package com.patchworkmc.curse.data;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurseAddonFile {

	@JsonProperty("isAvailable")
	private boolean isAvailable;

	@JsonProperty("fileName")
	private String fileName;

	@JsonProperty("displayName")
	private String displayName;

	@JsonProperty("downloadUrl")
	private String downloadUrl;

	@JsonProperty("parentFileLegacyMappingId")
	private Object parentFileLegacyMappingId;

	@JsonProperty("exposeAsAlternative")
	private Object exposeAsAlternative;

	@JsonProperty("fileTypeId")
	private Object fileTypeId;

	@JsonProperty("restrictProjectFileAccess")
	private int restrictProjectFileAccess;

	@JsonProperty("renderCacheId")
	private int renderCacheId;

	@JsonProperty("gameVersionDateReleased")
	private Date gameVersionDateReleased;

	@JsonProperty("fileStatus")
	private int fileStatus;

	@JsonProperty("isServerPack")
	private boolean isServerPack;

	@JsonProperty("releaseType")
	private int releaseType;

	@JsonProperty("parentProjectFileId")
	private Object parentProjectFileId;

	@JsonProperty("alternateFileId")
	private int alternateFileId;

	@JsonProperty("hasInstallScript")
	private boolean hasInstallScript;

	@JsonProperty("fileLegacyMappingId")
	private Object fileLegacyMappingId;

	@JsonProperty("installMetadata")
	private Object installMetadata;

	@JsonProperty("id")
	private int id;

	@JsonProperty("fileLength")
	private int fileLength;

	@JsonProperty("serverPackFileId")
	private Object serverPackFileId;

	@JsonProperty("isAlternate")
	private boolean isAlternate;

	@JsonProperty("packageFingerprintId")
	private int packageFingerprintId;

	@JsonProperty("gameId")
	private int gameId;

	@JsonProperty("changelog")
	private Object changelog;

	@JsonProperty("isCompatibleWithClient")
	private boolean isCompatibleWithClient;

	@JsonProperty("fileDate")
	private Date fileDate;

	@JsonProperty("categorySectionPackageType")
	private int categorySectionPackageType;

	@JsonProperty("gameVersionMappingId")
	private int gameVersionMappingId;

	@JsonProperty("modules")
	private List<CurseModule> modules;

	@JsonProperty("dependencies")
	private List<Object> dependencies;

	@JsonProperty("projectStatus")
	private int projectStatus;

	@JsonProperty("packageFingerprint")
	private long packageFingerprint;

	@JsonProperty("gameVersionId")
	private int gameVersionId;

	@JsonProperty("sortableGameVersion")
	private List<CurseSortableGameVersion> sortableGameVersion;

	@JsonProperty("gameVersion")
	private List<String> gameVersion;

	@JsonProperty("projectId")
	private int projectId;

	public boolean isIsAvailable(){
		return isAvailable;
	}

	public String getFileName(){
		return fileName;
	}

	public String getDisplayName(){
		return displayName;
	}

	public String getDownloadUrl(){
		return downloadUrl;
	}

	public Object getParentFileLegacyMappingId(){
		return parentFileLegacyMappingId;
	}

	public Object getExposeAsAlternative(){
		return exposeAsAlternative;
	}

	public Object getFileTypeId(){
		return fileTypeId;
	}

	public int getRestrictProjectFileAccess(){
		return restrictProjectFileAccess;
	}

	public int getRenderCacheId(){
		return renderCacheId;
	}

	public Date getGameVersionDateReleased(){
		return gameVersionDateReleased;
	}

	public int getFileStatus(){
		return fileStatus;
	}

	public boolean isIsServerPack(){
		return isServerPack;
	}

	public int getReleaseType(){
		return releaseType;
	}

	public Object getParentProjectFileId(){
		return parentProjectFileId;
	}

	public int getAlternateFileId(){
		return alternateFileId;
	}

	public boolean isHasInstallScript(){
		return hasInstallScript;
	}

	public Object getFileLegacyMappingId(){
		return fileLegacyMappingId;
	}

	public Object getInstallMetadata(){
		return installMetadata;
	}

	public int getId(){
		return id;
	}

	public int getFileLength(){
		return fileLength;
	}

	public Object getServerPackFileId(){
		return serverPackFileId;
	}

	public boolean isIsAlternate(){
		return isAlternate;
	}

	public int getPackageFingerprintId(){
		return packageFingerprintId;
	}

	public int getGameId(){
		return gameId;
	}

	public Object getChangelog(){
		return changelog;
	}

	public boolean isIsCompatibleWithClient(){
		return isCompatibleWithClient;
	}

	public Date getFileDate(){
		return fileDate;
	}

	public int getCategorySectionPackageType(){
		return categorySectionPackageType;
	}

	public int getGameVersionMappingId(){
		return gameVersionMappingId;
	}

	public List<CurseModule> getModules(){
		return modules;
	}

	public List<Object> getDependencies(){
		return dependencies;
	}

	public int getProjectStatus(){
		return projectStatus;
	}

	public long getPackageFingerprint(){
		return packageFingerprint;
	}

	public int getGameVersionId(){
		return gameVersionId;
	}

	public List<CurseSortableGameVersion> getSortableGameVersion(){
		return sortableGameVersion;
	}

	public List<String> getGameVersion(){
		return gameVersion;
	}

	public int getProjectId(){
		return projectId;
	}

	@Override
 	public String toString(){
		return 
			"CurseAddonFile{" +
			"isAvailable = '" + isAvailable + '\'' + 
			",fileName = '" + fileName + '\'' + 
			",displayName = '" + displayName + '\'' + 
			",downloadUrl = '" + downloadUrl + '\'' + 
			",parentFileLegacyMappingId = '" + parentFileLegacyMappingId + '\'' + 
			",exposeAsAlternative = '" + exposeAsAlternative + '\'' + 
			",fileTypeId = '" + fileTypeId + '\'' + 
			",restrictProjectFileAccess = '" + restrictProjectFileAccess + '\'' + 
			",renderCacheId = '" + renderCacheId + '\'' + 
			",gameVersionDateReleased = '" + gameVersionDateReleased + '\'' + 
			",fileStatus = '" + fileStatus + '\'' + 
			",isServerPack = '" + isServerPack + '\'' + 
			",releaseType = '" + releaseType + '\'' + 
			",parentProjectFileId = '" + parentProjectFileId + '\'' + 
			",alternateFileId = '" + alternateFileId + '\'' + 
			",hasInstallScript = '" + hasInstallScript + '\'' + 
			",fileLegacyMappingId = '" + fileLegacyMappingId + '\'' + 
			",installMetadata = '" + installMetadata + '\'' + 
			",id = '" + id + '\'' + 
			",fileLength = '" + fileLength + '\'' + 
			",serverPackFileId = '" + serverPackFileId + '\'' + 
			",isAlternate = '" + isAlternate + '\'' + 
			",packageFingerprintId = '" + packageFingerprintId + '\'' + 
			",gameId = '" + gameId + '\'' + 
			",changelog = '" + changelog + '\'' + 
			",isCompatibleWithClient = '" + isCompatibleWithClient + '\'' + 
			",fileDate = '" + fileDate + '\'' + 
			",categorySectionPackageType = '" + categorySectionPackageType + '\'' + 
			",gameVersionMappingId = '" + gameVersionMappingId + '\'' + 
			",modules = '" + modules + '\'' + 
			",dependencies = '" + dependencies + '\'' + 
			",projectStatus = '" + projectStatus + '\'' + 
			",packageFingerprint = '" + packageFingerprint + '\'' + 
			",gameVersionId = '" + gameVersionId + '\'' + 
			",sortableGameVersion = '" + sortableGameVersion + '\'' + 
			",gameVersion = '" + gameVersion + '\'' + 
			",projectId = '" + projectId + '\'' + 
			"}";
		}
}