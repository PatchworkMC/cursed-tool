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
public class CurseMinecraftVersion{

	@JsonProperty("approved")
	private boolean approved;

	@JsonProperty("jarDownloadUrl")
	private String jarDownloadUrl;

	@JsonProperty("gameVersionTypeStatus")
	private int gameVersionTypeStatus;

	@JsonProperty("gameVersionId")
	private int gameVersionId;

	@JsonProperty("versionString")
	private String versionString;

	@JsonProperty("jsonDownloadUrl")
	private String jsonDownloadUrl;

	@JsonProperty("gameVersionStatus")
	private int gameVersionStatus;

	@JsonProperty("dateModified")
	private Date dateModified;

	@JsonProperty("id")
	private int id;

	@JsonProperty("gameVersionTypeId")
	private int gameVersionTypeId;

	public boolean isApproved(){
		return approved;
	}

	public String getJarDownloadUrl(){
		return jarDownloadUrl;
	}

	public int getGameVersionTypeStatus(){
		return gameVersionTypeStatus;
	}

	public int getGameVersionId(){
		return gameVersionId;
	}

	public String getVersionString(){
		return versionString;
	}

	public String getJsonDownloadUrl(){
		return jsonDownloadUrl;
	}

	public int getGameVersionStatus(){
		return gameVersionStatus;
	}

	public Date getDateModified(){
		return dateModified;
	}

	public int getId(){
		return id;
	}

	public int getGameVersionTypeId(){
		return gameVersionTypeId;
	}

	@Override
 	public String toString(){
		return 
			"CurseMinecraftVersion{" + 
			"approved = '" + approved + '\'' + 
			",jarDownloadUrl = '" + jarDownloadUrl + '\'' + 
			",gameVersionTypeStatus = '" + gameVersionTypeStatus + '\'' + 
			",gameVersionId = '" + gameVersionId + '\'' + 
			",versionString = '" + versionString + '\'' + 
			",jsonDownloadUrl = '" + jsonDownloadUrl + '\'' + 
			",gameVersionStatus = '" + gameVersionStatus + '\'' + 
			",dateModified = '" + dateModified + '\'' + 
			",id = '" + id + '\'' + 
			",gameVersionTypeId = '" + gameVersionTypeId + '\'' + 
			"}";
		}
}