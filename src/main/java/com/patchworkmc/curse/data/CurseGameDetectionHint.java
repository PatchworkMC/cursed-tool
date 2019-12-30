package com.patchworkmc.curse.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseGameDetectionHint {

	@JsonProperty("hintKey")
	private Object hintKey;

	@JsonProperty("hintOptions")
	private int hintOptions;

	@JsonProperty("gameId")
	private int gameId;

	@JsonProperty("hintType")
	private int hintType;

	@JsonProperty("hintPath")
	private String hintPath;

	@JsonProperty("id")
	private int id;

	public Object getHintKey(){
		return hintKey;
	}

	public int getHintOptions(){
		return hintOptions;
	}

	public int getGameId(){
		return gameId;
	}

	public int getHintType(){
		return hintType;
	}

	public String getHintPath(){
		return hintPath;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"CurseGameDetectionHint{" +
			"hintKey = '" + hintKey + '\'' + 
			",hintOptions = '" + hintOptions + '\'' + 
			",gameId = '" + gameId + '\'' + 
			",hintType = '" + hintType + '\'' + 
			",hintPath = '" + hintPath + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}