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
public class CurseAuthor {

	@JsonProperty("twitchId")
	private int twitchId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("projectTitleId")
	private Object projectTitleId;

	@JsonProperty("id")
	private int id;

	@JsonProperty("projectTitleTitle")
	private Object projectTitleTitle;

	@JsonProperty("projectId")
	private int projectId;

	@JsonProperty("userId")
	private int userId;

	@JsonProperty("url")
	private String url;

	public int getTwitchId(){
		return twitchId;
	}

	public String getName(){
		return name;
	}

	public Object getProjectTitleId(){
		return projectTitleId;
	}

	public int getId(){
		return id;
	}

	public Object getProjectTitleTitle(){
		return projectTitleTitle;
	}

	public int getProjectId(){
		return projectId;
	}

	public int getUserId(){
		return userId;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"CurseAuthor{" +
			"twitchId = '" + twitchId + '\'' + 
			",name = '" + name + '\'' + 
			",projectTitleId = '" + projectTitleId + '\'' + 
			",id = '" + id + '\'' + 
			",projectTitleTitle = '" + projectTitleTitle + '\'' + 
			",projectId = '" + projectId + '\'' + 
			",userId = '" + userId + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}