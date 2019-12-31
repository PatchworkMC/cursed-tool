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
public class CurseAttachment {

	@JsonProperty("isDefault")
	private boolean isDefault;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private int id;

	@JsonProperty("title")
	private String title;

	@JsonProperty("projectId")
	private int projectId;

	@JsonProperty("url")
	private String url;

	@JsonProperty("thumbnailUrl")
	private String thumbnailUrl;

	@JsonProperty("status")
	private int status;

	public boolean isIsDefault(){
		return isDefault;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public int getProjectId(){
		return projectId;
	}

	public String getUrl(){
		return url;
	}

	public String getThumbnailUrl(){
		return thumbnailUrl;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"CurseAttachment{" +
			"isDefault = '" + isDefault + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",projectId = '" + projectId + '\'' + 
			",url = '" + url + '\'' + 
			",thumbnailUrl = '" + thumbnailUrl + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}