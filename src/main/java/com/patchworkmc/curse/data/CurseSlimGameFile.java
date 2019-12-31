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

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseSlimGameFile {

	@JsonProperty("gameId")
	private int gameId;

	@JsonProperty("isRequired")
	private boolean isRequired;

	@JsonProperty("fileName")
	private String fileName;

	@JsonProperty("platformType")
	private int platformType;

	@JsonProperty("id")
	private int id;

	@JsonProperty("fileType")
	private int fileType;

	public int getGameId(){
		return gameId;
	}

	public boolean isIsRequired(){
		return isRequired;
	}

	public String getFileName(){
		return fileName;
	}

	public int getPlatformType(){
		return platformType;
	}

	public int getId(){
		return id;
	}

	public int getFileType(){
		return fileType;
	}

	@Override
 	public String toString(){
		return 
			"CurseSlimGameFile{" +
			"gameId = '" + gameId + '\'' + 
			",isRequired = '" + isRequired + '\'' + 
			",fileName = '" + fileName + '\'' + 
			",platformType = '" + platformType + '\'' + 
			",id = '" + id + '\'' + 
			",fileType = '" + fileType + '\'' + 
			"}";
		}
}