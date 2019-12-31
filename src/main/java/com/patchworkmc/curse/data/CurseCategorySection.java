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
public class CurseCategorySection {

	@JsonProperty("gameId")
	private int gameId;

	@JsonProperty("path")
	private String path;

	@JsonProperty("initialInclusionPattern")
	private String initialInclusionPattern;

	@JsonProperty("extraIncludePattern")
	private String extraIncludePattern;

	@JsonProperty("gameCategoryId")
	private int gameCategoryId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	@JsonProperty("packageType")
	private int packageType;

	public int getGameId(){
		return gameId;
	}

	public String getPath(){
		return path;
	}

	public String getInitialInclusionPattern(){
		return initialInclusionPattern;
	}

	public String getExtraIncludePattern(){
		return extraIncludePattern;
	}

	public int getGameCategoryId(){
		return gameCategoryId;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public int getPackageType(){
		return packageType;
	}

	@Override
 	public String toString(){
		return 
			"CurseCategorySection{" +
			"gameId = '" + gameId + '\'' + 
			",path = '" + path + '\'' + 
			",initialInclusionPattern = '" + initialInclusionPattern + '\'' + 
			",extraIncludePattern = '" + extraIncludePattern + '\'' + 
			",gameCategoryId = '" + gameCategoryId + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",packageType = '" + packageType + '\'' + 
			"}";
		}
}