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