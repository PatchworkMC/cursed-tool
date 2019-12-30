package com.patchworkmc.curse;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.patchworkmc.curse.data.CurseAddon;
import com.patchworkmc.curse.data.CurseAddonFile;
import com.patchworkmc.curse.data.CurseCategory;
import com.patchworkmc.curse.data.CurseGame;
import com.patchworkmc.json.JsonConverterException;
import com.patchworkmc.network.http.HttpClient;
import com.patchworkmc.network.http.HttpException;

public class CurseApi {
	public static final String BASE_URL = "https://addons-ecs.forgesvc.net/api/v2";

	private final HttpClient httpClient;

	private static String makeEndpoint(String format, Object... args) {
		return BASE_URL + "/" + String.format(format, args);
	}

	/**
	 * Creates a new CurseApi object for interaction with the curse API.
	 *
	 * @param httpClient The http client to use for web request
	 */
	public CurseApi(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * Retrieves an addon.
	 *
	 * @param addonId The id of the addon to retrieve
	 * @return The addon
	 * @throws CurseException If the addon was not found or another error occurred
	 */
	public CurseAddon getAddon(int addonId) throws CurseException {
		try {
			return httpClient.get(makeEndpoint("addon/%d", addonId)).executeAndParseJson(CurseAddon.class);
		} catch (HttpException | JsonConverterException e) {
			throw new CurseException("Failed to retrieve addon", e);
		}
	}

	/**
	 * Searches addons on the Curse api. This is done in an array like manner
	 * when it comes to indices.
	 *
	 * @param gameId      The id of the game to search addons for
	 * @param gameVersion The version of the game to search addons for
	 * @param index       The index to start searching from
	 * @param pageSize    The maximum amount of results to retrieve
	 * @param sectionId   The section id the addons be long to
	 *                    WARNING: This is the id of the category retrieved
	 *                    using {@link #getCategories()}
	 * @return Maximal {@code pageSize} addons meeting the criteria
	 * @throws CurseException If an error occurs while searching for addons
	 */
	public List<CurseAddon> searchAddons(int gameId, String gameVersion, int index, int pageSize, int sectionId)
			throws CurseException {
		try {
			return Arrays.asList(httpClient
					.get(makeEndpoint("addon/search"))
					.query("categoryID", 0)
					.query("gameId", gameId)
					.query("gameVersion", gameVersion)
					.query("index", index)
					.query("pageSize", pageSize)
					.query("sectionId", sectionId)
					.query("sort", 0)
					.executeAndParseJson(CurseAddon[].class));
		} catch (HttpException | JsonConverterException e) {
			throw new CurseException("Failed to retrieve addon", e);
		}
	}

	/**
	 * Retrieves multiple addons.
	 *
	 * @param ids The id's of the addons to retrieve
	 * @return A list of addons matching the id's
	 * @throws CurseException If an error occurred while retrieving the addons
	 */
	public List<CurseAddon> getMultipleAddons(int[] ids) throws CurseException {
		try {
			return Arrays.asList(httpClient.post(makeEndpoint("addon"))
					.jsonBody(ids).header("Content-Type", "application/json").executeAndParseJson(CurseAddon[].class));
		} catch (HttpException | JsonConverterException e) {
			throw new CurseException("Failed to retrieve multiple addons", e);
		}
	}

	/**
	 * Retrieves a list of games from curse.
	 *
	 * @return The games available on curse
	 * @throws CurseException If an error occurs while retrieving the games
	 */
	public List<CurseGame> getGames() throws CurseException {
		try {
			return Arrays.asList(httpClient.get(makeEndpoint("game")).executeAndParseJson(CurseGame[].class));
		} catch (HttpException | JsonConverterException e) {
			throw new CurseException("Failed to retrieve list of games", e);
		}
	}

	/**
	 * Retrieves a list of categories from curse.
	 *
	 * @return The categories available on curse
	 * @throws CurseException If an error occurs while retrieving the categories
	 */
	public List<CurseCategory> getCategories() throws CurseException {
		try {
			return Arrays.asList(httpClient.get(makeEndpoint("category")).executeAndParseJson(CurseCategory[].class));
		} catch (HttpException | JsonConverterException e) {
			throw new CurseException("Failed to retrieve list of categories", e);
		}
	}

	/**
	 * Retrieves a list of files for a specific addon.
	 *
	 * @param id The id of the addon to retrieve the files for
	 * @return All files of the addon
	 * @throws CurseException If an error occurs while retrieving the files
	 */
	public List<CurseAddonFile> getAddonFiles(int id) throws CurseException {
		try {
			return Arrays.asList(httpClient.get(makeEndpoint("addon/%d/files", id))
					.executeAndParseJson(CurseAddonFile[].class));
		} catch (HttpException | JsonConverterException e) {
			throw new CurseException("Failed to retrieve list of files for addon", e);
		}
	}

	/**
	 * Opens a stream to download a file from curse.
	 *
	 * @param file The file to download
	 * @return An {@link InputStream} streaming the file data
	 */
	public InputStream downloadFile(CurseAddonFile file) throws CurseException {
		try {
			return httpClient.get(file.getDownloadUrl()).executeAndGetStream();
		} catch (HttpException e) {
			throw new CurseException("Failed to retrieve list of files for addon", e);
		}
	}
}
