package com.patchworkmc.curse.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseSlimGameFile {

	@JsonProperty("gameId")
	private int gameId;

	@JsonProperty("isRequired")
	private boolean isRequired;

	@JsonProperty("fileName")
	private String fileName;

	@JsonProperty("platformType")
	private int platformType;

	@JsonProperty("id")
	private int id;

	@JsonProperty("fileType")
	private int fileType;

	public int getGameId(){
		return gameId;
	}

	public boolean isIsRequired(){
		return isRequired;
	}

	public String getFileName(){
		return fileName;
	}

	public int getPlatformType(){
		return platformType;
	}

	public int getId(){
		return id;
	}

	public int getFileType(){
		return fileType;
	}

	@Override
 	public String toString(){
		return 
			"CurseSlimGameFile{" +
			"gameId = '" + gameId + '\'' + 
			",isRequired = '" + isRequired + '\'' + 
			",fileName = '" + fileName + '\'' + 
			",platformType = '" + platformType + '\'' + 
			",id = '" + id + '\'' + 
			",fileType = '" + fileType + '\'' + 
			"}";
		}
}