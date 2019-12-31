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

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseCategory{

	@JsonProperty("gameId")
	private int gameId;

	@JsonProperty("parentGameCategoryId")
	private int parentGameCategoryId;

	@JsonProperty("avatarUrl")
	private String avatarUrl;

	@JsonProperty("rootGameCategoryId")
	private int rootGameCategoryId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("dateModified")
	private Date dateModified;

	@JsonProperty("id")
	private int id;

	@JsonProperty("slug")
	private String slug;

	public int getGameId(){
		return gameId;
	}

	public int getParentGameCategoryId(){
		return parentGameCategoryId;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public int getRootGameCategoryId(){
		return rootGameCategoryId;
	}

	public String getName(){
		return name;
	}

	public Date getDateModified(){
		return dateModified;
	}

	public int getId(){
		return id;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"CurseCategory{" + 
			"gameId = '" + gameId + '\'' + 
			",parentGameCategoryId = '" + parentGameCategoryId + '\'' + 
			",avatarUrl = '" + avatarUrl + '\'' + 
			",rootGameCategoryId = '" + rootGameCategoryId + '\'' + 
			",name = '" + name + '\'' + 
			",dateModified = '" + dateModified + '\'' + 
			",id = '" + id + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}