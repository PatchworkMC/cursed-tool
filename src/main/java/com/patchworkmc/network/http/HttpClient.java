package com.patchworkmc.network.http;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import com.patchworkmc.network.http.request.HttpGetRequest;
import com.patchworkmc.network.http.request.HttpPostRequest;
import com.patchworkmc.network.http.request.HttpRequest;

public class HttpClient {
	private final CookieManager cookieManager;

	public HttpClient() {
		this.cookieManager = new CookieManager();
	}

	public HttpURLConnection openConnection(HttpRequest<?> request) throws HttpException {
		try {
			URL url = request.buildUrl();
			request.validate();
			URLConnection connection = url.openConnection();

			if (!(connection instanceof HttpURLConnection)) {
				throw new HttpException("Opening URL " + url.toExternalForm() + " didn't yield a HTTP connection");
			} else {
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				request.applyHeaders(httpConnection);
				httpConnection.setRequestProperty("Cookie",
						cookieManager
								.getCookieStore()
								.get(url.toURI())
								.stream()
								.map(HttpCookie::toString)
								.collect(Collectors.joining(";")));
				return httpConnection;
			}
		} catch (IOException e) {
			throw new HttpException("IOException while opening url connection", e);
		} catch (URISyntaxException e) {
			throw new HttpException("URISyntax exception while converting URL to URI", e);
		}
	}

	public HttpGetRequest get() {
		return new HttpGetRequest(this);
	}

	public HttpGetRequest get(String url) {
		return new HttpGetRequest(this).url(url);
	}

	public HttpPostRequest post() {
		return new HttpPostRequest(this);
	}

	public HttpPostRequest post(String url) {
		return new HttpPostRequest(this).url(url);
	}
}
