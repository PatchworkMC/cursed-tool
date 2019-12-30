package com.patchworkmc.curse.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurseLatestFileForGameVersion {

	@JsonProperty("projectFileName")
	private String projectFileName;

	@JsonProperty("projectFileId")
	private int projectFileId;

	@JsonProperty("gameVersion")
	private String gameVersion;

	@JsonProperty("fileType")
	private int fileType;

	public String getProjectFileName(){
		return projectFileName;
	}

	public int getProjectFileId(){
		return projectFileId;
	}

	public String getGameVersion(){
		return gameVersion;
	}

	public int getFileType(){
		return fileType;
	}

	@Override
 	public String toString(){
		return 
			"CurseLatestFileForGameVersion{" +
			"projectFileName = '" + projectFileName + '\'' + 
			",projectFileId = '" + projectFileId + '\'' + 
			",gameVersion = '" + gameVersion + '\'' + 
			",fileType = '" + fileType + '\'' + 
			"}";
		}
}