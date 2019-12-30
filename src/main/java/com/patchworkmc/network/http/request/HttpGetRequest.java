package com.patchworkmc.network.http.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.patchworkmc.json.JsonConverter;
import com.patchworkmc.json.JsonConverterException;
import com.patchworkmc.network.http.HttpClient;
import com.patchworkmc.network.http.HttpException;

public class HttpGetRequest extends HttpRequest<HttpGetRequest> {
	public HttpGetRequest(HttpClient client) {
		super(client);
	}

	public InputStream executeAndGetStream() throws HttpException {
		HttpURLConnection urlConnection = client.openConnection(this);

		try {
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new HttpException("Server returned HTTP status code " + urlConnection.getResponseCode());
			}

			return urlConnection.getInputStream();
		} catch (IOException e) {
			throw new HttpException("IOException while connecting to " + urlConnection.getURL().toExternalForm(), e);
		}
	}

	public <T> T executeAndParseJson(Class<T> targetClass) throws HttpException, JsonConverterException {
		HttpURLConnection urlConnection = client.openConnection(this);

		try {
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new HttpException("Server returned HTTP status code " + urlConnection.getResponseCode());
			}

			return JsonConverter.streamToObject(urlConnection.getInputStream(), targetClass);
		} catch (IOException e) {
			throw new HttpException("IOException while connecting to " + urlConnection.getURL().toExternalForm(), e);
		} finally {
			urlConnection.disconnect();
		}
	}
}
