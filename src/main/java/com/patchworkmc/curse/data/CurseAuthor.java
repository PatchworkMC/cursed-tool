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