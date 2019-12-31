/*
 * Patchwork Project
 * Copyright (C) 2019 PatchworkMC and contributors
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.patchworkmc.curse.data;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurseAddon {

	@JsonProperty("popularityScore")
	private double popularityScore;

	@JsonProperty("isAvailable")
	private boolean isAvailable;

	@JsonProperty("attachments")
	private List<CurseAttachment> attachments;

	@JsonProperty("categorySection")
	private CurseCategorySection categorySection;

	@JsonProperty("gameVersionLatestFiles")
	private List<CurseLatestFileForGameVersion> gameVersionLatestFiles;

	@JsonProperty("isExperiemental")
	private boolean isExperiemental;

	@JsonProperty("dateCreated")
	private Date dateCreated;

	@JsonProperty("gameName")
	private String gameName;

	@JsonProperty("portalName")
	private String portalName;

	@JsonProperty("websiteUrl")
	private String websiteUrl;

	@JsonProperty("dateReleased")
	private Date dateReleased;

	@JsonProperty("id")
	private int id;

	@JsonProperty("categories")
	private List<CurseAddonCategory> categories;

	@JsonProperty("isFeatured")
	private boolean isFeatured;

	@JsonProperty("slug")
	private String slug;

	@JsonProperty("gameId")
	private int gameId;

	@JsonProperty("summary")
	private String summary;

	@JsonProperty("latestFiles")
	private List<CurseAddonFile> latestFiles;

	@JsonProperty("defaultFileId")
	private int defaultFileId;

	@JsonProperty("dateModified")
	private Date dateModified;

	@JsonProperty("gamePopularityRank")
	private int gamePopularityRank;

	@JsonProperty("gameSlug")
	private String gameSlug;

	@JsonProperty("name")
	private String name;

	@JsonProperty("primaryCategoryId")
	private int primaryCategoryId;

	@JsonProperty("downloadCount")
	private int downloadCount;

	@JsonProperty("primaryLanguage")
	private String primaryLanguage;

	@JsonProperty("authors")
	private List<CurseAuthor> authors;

	@JsonProperty("status")
	private int status;

	public double getPopularityScore(){
		return popularityScore;
	}

	public boolean isIsAvailable(){
		return isAvailable;
	}

	public List<CurseAttachment> getAttachments(){
		return attachments;
	}

	public CurseCategorySection getCategorySection(){
		return categorySection;
	}

	public List<CurseLatestFileForGameVersion> getGameVersionLatestFiles(){
		return gameVersionLatestFiles;
	}

	public boolean isIsExperiemental(){
		return isExperiemental;
	}

	public Date getDateCreated(){
		return dateCreated;
	}

	public String getGameName(){
		return gameName;
	}

	public String getPortalName(){
		return portalName;
	}

	public String getWebsiteUrl(){
		return websiteUrl;
	}

	public Date getDateReleased(){
		return dateReleased;
	}

	public int getId(){
		return id;
	}

	public List<CurseAddonCategory> getCategories(){
		return categories;
	}

	public boolean isIsFeatured(){
		return isFeatured;
	}

	public String getSlug(){
		return slug;
	}

	public int getGameId(){
		return gameId;
	}

	public String getSummary(){
		return summary;
	}

	public List<CurseAddonFile> getLatestFiles(){
		return latestFiles;
	}

	public int getDefaultFileId(){
		return defaultFileId;
	}

	public Date getDateModified(){
		return dateModified;
	}

	public int getGamePopularityRank(){
		return gamePopularityRank;
	}

	public String getGameSlug(){
		return gameSlug;
	}

	public String getName(){
		return name;
	}

	public int getPrimaryCategoryId(){
		return primaryCategoryId;
	}

	public int getDownloadCount(){
		return downloadCount;
	}

	public String getPrimaryLanguage(){
		return primaryLanguage;
	}

	public List<CurseAuthor> getAuthors(){
		return authors;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"CurseAddon{" +
			"popularityScore = '" + popularityScore + '\'' + 
			",isAvailable = '" + isAvailable + '\'' + 
			",attachments = '" + attachments + '\'' + 
			",categorySection = '" + categorySection + '\'' + 
			",gameVersionLatestFiles = '" + gameVersionLatestFiles + '\'' + 
			",isExperiemental = '" + isExperiemental + '\'' + 
			",dateCreated = '" + dateCreated + '\'' + 
			",gameName = '" + gameName + '\'' + 
			",portalName = '" + portalName + '\'' + 
			",websiteUrl = '" + websiteUrl + '\'' + 
			",dateReleased = '" + dateReleased + '\'' + 
			",id = '" + id + '\'' + 
			",categories = '" + categories + '\'' + 
			",isFeatured = '" + isFeatured + '\'' + 
			",slug = '" + slug + '\'' + 
			",gameId = '" + gameId + '\'' + 
			",summary = '" + summary + '\'' + 
			",latestFiles = '" + latestFiles + '\'' + 
			",defaultFileId = '" + defaultFileId + '\'' + 
			",dateModified = '" + dateModified + '\'' + 
			",gamePopularityRank = '" + gamePopularityRank + '\'' + 
			",gameSlug = '" + gameSlug + '\'' + 
			",name = '" + name + '\'' + 
			",primaryCategoryId = '" + primaryCategoryId + '\'' + 
			",downloadCount = '" + downloadCount + '\'' + 
			",primaryLanguage = '" + primaryLanguage + '\'' + 
			",authors = '" + authors + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}