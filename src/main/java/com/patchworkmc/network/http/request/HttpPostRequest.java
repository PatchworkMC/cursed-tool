package com.patchworkmc.network.http.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.patchworkmc.json.JsonConverter;
import com.patchworkmc.json.JsonConverterException;
import com.patchworkmc.network.http.HttpClient;
import com.patchworkmc.network.http.HttpException;
import com.patchworkmc.util.IThrowingSupplier;

public class HttpPostRequest extends HttpRequest<HttpPostRequest> {
	private IThrowingSupplier<String, HttpException> body;

	public HttpPostRequest(HttpClient client) {
		super(client);
	}

	public HttpPostRequest body(String body) {
		this.body = () -> body;
		return this;
	}

	public HttpPostRequest jsonBody(Object body) {
		this.body = () -> {
			try {
				return JsonConverter.OBJECT_MAPPER.writeValueAsString(body);
			} catch (JsonProcessingException e) {
				throw new HttpException("Failed to convert body to json");
			}
		};
		return this;
	}

	public <T> T executeAndParseJson(Class<T> targetClass) throws HttpException, JsonConverterException {
		HttpURLConnection urlConnection = client.openConnection(this);

		try {
			urlConnection.setRequestMethod("POST");

			String realBody = body != null ? body.get() : null;

			urlConnection.connect();

			if (realBody != null) {
				urlConnection.setDoOutput(true);
				urlConnection.getOutputStream().write(realBody.getBytes(StandardCharsets.UTF_8));
				urlConnection.getOutputStream().flush();
			}

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
