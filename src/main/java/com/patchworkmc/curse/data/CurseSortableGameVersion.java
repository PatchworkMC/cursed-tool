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

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseSortableGameVersion {

	@JsonProperty("gameVersionPadded")
	private String gameVersionPadded;

	@JsonProperty("gameVersion")
	private String gameVersion;

	@JsonProperty("gameVersionReleaseDate")
	private String gameVersionReleaseDate;

	@JsonProperty("gameVersionName")
	private String gameVersionName;

	public String getGameVersionPadded(){
		return gameVersionPadded;
	}

	public String getGameVersion(){
		return gameVersion;
	}

	public String getGameVersionReleaseDate(){
		return gameVersionReleaseDate;
	}

	public String getGameVersionName(){
		return gameVersionName;
	}

	@Override
 	public String toString(){
		return 
			"CurseSortableGameVersion{" +
			"gameVersionPadded = '" + gameVersionPadded + '\'' + 
			",gameVersion = '" + gameVersion + '\'' + 
			",gameVersionReleaseDate = '" + gameVersionReleaseDate + '\'' + 
			",gameVersionName = '" + gameVersionName + '\'' + 
			"}";
		}
}