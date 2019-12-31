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

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseModloader{

	@JsonProperty("forgeVersion")
	private String forgeVersion;

	@JsonProperty("downloadUrl")
	private String downloadUrl;

	@JsonProperty("modLoaderGameVersionTypeStatus")
	private int modLoaderGameVersionTypeStatus;

	@JsonProperty("type")
	private int type;

	@JsonProperty("mcGameVersionId")
	private int mcGameVersionId;

	@JsonProperty("librariesInstallLocation")
	private String librariesInstallLocation;

	@JsonProperty("approved")
	private boolean approved;

	@JsonProperty("installProfileJson")
	private Object installProfileJson;

	@JsonProperty("installMethod")
	private int installMethod;

	@JsonProperty("mcGameVersionTypeStatus")
	private int mcGameVersionTypeStatus;

	@JsonProperty("minecraftGameVersionId")
	private int minecraftGameVersionId;

	@JsonProperty("modLoaderGameVersionStatus")
	private int modLoaderGameVersionStatus;

	@JsonProperty("id")
	private int id;

	@JsonProperty("minecraftVersion")
	private String minecraftVersion;

	@JsonProperty("modLoaderGameVersionId")
	private int modLoaderGameVersionId;

	@JsonProperty("modLoaderGameVersionTypeId")
	private int modLoaderGameVersionTypeId;

	@JsonProperty("latest")
	private boolean latest;

	@JsonProperty("mavenVersionString")
	private String mavenVersionString;

	@JsonProperty("dateModified")
	private Date dateModified;

	@JsonProperty("mcGameVersionStatus")
	private int mcGameVersionStatus;

	@JsonProperty("versionJson")
	private String versionJson;

	@JsonProperty("recommended")
	private boolean recommended;

	@JsonProperty("mcGameVersionTypeId")
	private int mcGameVersionTypeId;

	@JsonProperty("additionalFilesJson")
	private Object additionalFilesJson;

	@JsonProperty("filename")
	private String filename;

	@JsonProperty("gameVersionId")
	private int gameVersionId;

	@JsonProperty("name")
	private String name;

	public String getForgeVersion(){
		return forgeVersion;
	}

	public String getDownloadUrl(){
		return downloadUrl;
	}

	public int getModLoaderGameVersionTypeStatus(){
		return modLoaderGameVersionTypeStatus;
	}

	public int getType(){
		return type;
	}

	public int getMcGameVersionId(){
		return mcGameVersionId;
	}

	public String getLibrariesInstallLocation(){
		return librariesInstallLocation;
	}

	public boolean isApproved(){
		return approved;
	}

	public Object getInstallProfileJson(){
		return installProfileJson;
	}

	public int getInstallMethod(){
		return installMethod;
	}

	public int getMcGameVersionTypeStatus(){
		return mcGameVersionTypeStatus;
	}

	public int getMinecraftGameVersionId(){
		return minecraftGameVersionId;
	}

	public int getModLoaderGameVersionStatus(){
		return modLoaderGameVersionStatus;
	}

	public int getId(){
		return id;
	}

	public String getMinecraftVersion(){
		return minecraftVersion;
	}

	public int getModLoaderGameVersionId(){
		return modLoaderGameVersionId;
	}

	public int getModLoaderGameVersionTypeId(){
		return modLoaderGameVersionTypeId;
	}

	public boolean isLatest(){
		return latest;
	}

	public String getMavenVersionString(){
		return mavenVersionString;
	}

	public Date getDateModified(){
		return dateModified;
	}

	public int getMcGameVersionStatus(){
		return mcGameVersionStatus;
	}

	public String getVersionJson(){
		return versionJson;
	}

	public boolean isRecommended(){
		return recommended;
	}

	public int getMcGameVersionTypeId(){
		return mcGameVersionTypeId;
	}

	public Object getAdditionalFilesJson(){
		return additionalFilesJson;
	}

	public String getFilename(){
		return filename;
	}

	public int getGameVersionId(){
		return gameVersionId;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"CurseModloader{" + 
			"forgeVersion = '" + forgeVersion + '\'' + 
			",downloadUrl = '" + downloadUrl + '\'' + 
			",modLoaderGameVersionTypeStatus = '" + modLoaderGameVersionTypeStatus + '\'' + 
			",type = '" + type + '\'' + 
			",mcGameVersionId = '" + mcGameVersionId + '\'' + 
			",librariesInstallLocation = '" + librariesInstallLocation + '\'' + 
			",approved = '" + approved + '\'' + 
			",installProfileJson = '" + installProfileJson + '\'' + 
			",installMethod = '" + installMethod + '\'' + 
			",mcGameVersionTypeStatus = '" + mcGameVersionTypeStatus + '\'' + 
			",minecraftGameVersionId = '" + minecraftGameVersionId + '\'' + 
			",modLoaderGameVersionStatus = '" + modLoaderGameVersionStatus + '\'' + 
			",id = '" + id + '\'' + 
			",minecraftVersion = '" + minecraftVersion + '\'' + 
			",modLoaderGameVersionId = '" + modLoaderGameVersionId + '\'' + 
			",modLoaderGameVersionTypeId = '" + modLoaderGameVersionTypeId + '\'' + 
			",latest = '" + latest + '\'' + 
			",mavenVersionString = '" + mavenVersionString + '\'' + 
			",dateModified = '" + dateModified + '\'' + 
			",mcGameVersionStatus = '" + mcGameVersionStatus + '\'' + 
			",versionJson = '" + versionJson + '\'' + 
			",recommended = '" + recommended + '\'' + 
			",mcGameVersionTypeId = '" + mcGameVersionTypeId + '\'' + 
			",additionalFilesJson = '" + additionalFilesJson + '\'' + 
			",filename = '" + filename + '\'' + 
			",gameVersionId = '" + gameVersionId + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}