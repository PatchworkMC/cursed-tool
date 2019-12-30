package com.patchworkmc.curse.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseMinecraftVersion{

	@JsonProperty("approved")
	private boolean approved;

	@JsonProperty("jarDownloadUrl")
	private String jarDownloadUrl;

	@JsonProperty("gameVersionTypeStatus")
	private int gameVersionTypeStatus;

	@JsonProperty("gameVersionId")
	private int gameVersionId;

	@JsonProperty("versionString")
	private String versionString;

	@JsonProperty("jsonDownloadUrl")
	private String jsonDownloadUrl;

	@JsonProperty("gameVersionStatus")
	private int gameVersionStatus;

	@JsonProperty("dateModified")
	private Date dateModified;

	@JsonProperty("id")
	private int id;

	@JsonProperty("gameVersionTypeId")
	private int gameVersionTypeId;

	public boolean isApproved(){
		return approved;
	}

	public String getJarDownloadUrl(){
		return jarDownloadUrl;
	}

	public int getGameVersionTypeStatus(){
		return gameVersionTypeStatus;
	}

	public int getGameVersionId(){
		return gameVersionId;
	}

	public String getVersionString(){
		return versionString;
	}

	public String getJsonDownloadUrl(){
		return jsonDownloadUrl;
	}

	public int getGameVersionStatus(){
		return gameVersionStatus;
	}

	public Date getDateModified(){
		return dateModified;
	}

	public int getId(){
		return id;
	}

	public int getGameVersionTypeId(){
		return gameVersionTypeId;
	}

	@Override
 	public String toString(){
		return 
			"CurseMinecraftVersion{" + 
			"approved = '" + approved + '\'' + 
			",jarDownloadUrl = '" + jarDownloadUrl + '\'' + 
			",gameVersionTypeStatus = '" + gameVersionTypeStatus + '\'' + 
			",gameVersionId = '" + gameVersionId + '\'' + 
			",versionString = '" + versionString + '\'' + 
			",jsonDownloadUrl = '" + jsonDownloadUrl + '\'' + 
			",gameVersionStatus = '" + gameVersionStatus + '\'' + 
			",dateModified = '" + dateModified + '\'' + 
			",id = '" + id + '\'' + 
			",gameVersionTypeId = '" + gameVersionTypeId + '\'' + 
			"}";
		}
}