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
public class CurseAddonCategory {

	@JsonProperty("gameId")
	private int gameId;

	@JsonProperty("avatarId")
	private int avatarId;

	@JsonProperty("avatarUrl")
	private String avatarUrl;

	@JsonProperty("rootId")
	private int rootId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("projectId")
	private int projectId;

	@JsonProperty("categoryId")
	private int categoryId;

	@JsonProperty("url")
	private String url;

	@JsonProperty("parentId")
	private int parentId;

	public int getGameId(){
		return gameId;
	}

	public int getAvatarId(){
		return avatarId;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public int getRootId(){
		return rootId;
	}

	public String getName(){
		return name;
	}

	public int getProjectId(){
		return projectId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public String getUrl(){
		return url;
	}

	public int getParentId(){
		return parentId;
	}

	@Override
 	public String toString(){
		return 
			"CurseAddonCategory{" +
			"gameId = '" + gameId + '\'' + 
			",avatarId = '" + avatarId + '\'' + 
			",avatarUrl = '" + avatarUrl + '\'' + 
			",rootId = '" + rootId + '\'' + 
			",name = '" + name + '\'' + 
			",projectId = '" + projectId + '\'' + 
			",categoryId = '" + categoryId + '\'' + 
			",url = '" + url + '\'' + 
			",parentId = '" + parentId + '\'' + 
			"}";
		}
}