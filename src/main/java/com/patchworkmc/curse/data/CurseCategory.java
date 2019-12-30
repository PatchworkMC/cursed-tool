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