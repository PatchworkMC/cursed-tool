package com.patchworkmc.curse.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseCategorySection {

	@JsonProperty("gameId")
	private int gameId;

	@JsonProperty("path")
	private String path;

	@JsonProperty("initialInclusionPattern")
	private String initialInclusionPattern;

	@JsonProperty("extraIncludePattern")
	private String extraIncludePattern;

	@JsonProperty("gameCategoryId")
	private int gameCategoryId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	@JsonProperty("packageType")
	private int packageType;

	public int getGameId(){
		return gameId;
	}

	public String getPath(){
		return path;
	}

	public String getInitialInclusionPattern(){
		return initialInclusionPattern;
	}

	public String getExtraIncludePattern(){
		return extraIncludePattern;
	}

	public int getGameCategoryId(){
		return gameCategoryId;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public int getPackageType(){
		return packageType;
	}

	@Override
 	public String toString(){
		return 
			"CurseCategorySection{" +
			"gameId = '" + gameId + '\'' + 
			",path = '" + path + '\'' + 
			",initialInclusionPattern = '" + initialInclusionPattern + '\'' + 
			",extraIncludePattern = '" + extraIncludePattern + '\'' + 
			",gameCategoryId = '" + gameCategoryId + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",packageType = '" + packageType + '\'' + 
			"}";
		}
}