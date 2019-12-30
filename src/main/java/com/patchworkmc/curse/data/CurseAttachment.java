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