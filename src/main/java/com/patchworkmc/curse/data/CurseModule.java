package com.patchworkmc.curse.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseModule {

	@JsonProperty("fingerprint")
	private long fingerprint;

	@JsonProperty("foldername")
	private String foldername;

	@JsonProperty("type")
	private int type;

	public long getFingerprint(){
		return fingerprint;
	}

	public String getFoldername(){
		return foldername;
	}

	public int getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"CurseModule{" +
			"fingerprint = '" + fingerprint + '\'' + 
			",foldername = '" + foldername + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}