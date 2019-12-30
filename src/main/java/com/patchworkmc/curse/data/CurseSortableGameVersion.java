package com.patchworkmc.curse.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseSortableGameVersion {

	@JsonProperty("gameVersionPadded")
	private String gameVersionPadded;

	@JsonProperty("gameVersion")
	private String gameVersion;

	@JsonProperty("gameVersionReleaseDate")
	private String gameVersionReleaseDate;

	@JsonProperty("gameVersionName")
	private String gameVersionName;

	public String getGameVersionPadded(){
		return gameVersionPadded;
	}

	public String getGameVersion(){
		return gameVersion;
	}

	public String getGameVersionReleaseDate(){
		return gameVersionReleaseDate;
	}

	public String getGameVersionName(){
		return gameVersionName;
	}

	@Override
 	public String toString(){
		return 
			"CurseSortableGameVersion{" +
			"gameVersionPadded = '" + gameVersionPadded + '\'' + 
			",gameVersion = '" + gameVersion + '\'' + 
			",gameVersionReleaseDate = '" + gameVersionReleaseDate + '\'' + 
			",gameVersionName = '" + gameVersionName + '\'' + 
			"}";
		}
}