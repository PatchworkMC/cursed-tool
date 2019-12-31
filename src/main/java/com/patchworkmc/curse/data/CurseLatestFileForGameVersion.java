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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurseLatestFileForGameVersion {

	@JsonProperty("projectFileName")
	private String projectFileName;

	@JsonProperty("projectFileId")
	private int projectFileId;

	@JsonProperty("gameVersion")
	private String gameVersion;

	@JsonProperty("fileType")
	private int fileType;

	public String getProjectFileName(){
		return projectFileName;
	}

	public int getProjectFileId(){
		return projectFileId;
	}

	public String getGameVersion(){
		return gameVersion;
	}

	public int getFileType(){
		return fileType;
	}

	@Override
 	public String toString(){
		return 
			"CurseLatestFileForGameVersion{" +
			"projectFileName = '" + projectFileName + '\'' + 
			",projectFileId = '" + projectFileId + '\'' + 
			",gameVersion = '" + gameVersion + '\'' + 
			",fileType = '" + fileType + '\'' + 
			"}";
		}
}