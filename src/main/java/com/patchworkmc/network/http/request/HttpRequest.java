package com.patchworkmc.network.http.request;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.patchworkmc.network.http.HttpClient;
import com.patchworkmc.network.http.HttpException;

public abstract class HttpRequest<S> {
	protected final HttpClient client;

	protected Exception exception;

	protected String protocol;
	protected String host;
	protected int port;
	protected String path;
	protected Map<String, String> query;
	protected Map<String, String> headers;

	HttpRequest(HttpClient client) {
		this.client = client;
		this.port = -2;
		this.query = new HashMap<>();
		this.headers = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public S url(String url) {
		try {
			return url(new URL(url));
		} catch (MalformedURLException e) {
			protocol = null;
			host = null;
			port = -1;
			query.clear();

			exception = e;
		}

		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S url(URL url) {
		URI uri;

		try {
			uri = url.toURI();
		} catch (URISyntaxException e) {
			exception = e;
			return (S) this;
		}

		exception = null;

		protocol = url.getProtocol();
		host = url.getHost();
		port = url.getPort();
		path = url.getPath();

		if (port == -1) {
			port = url.getDefaultPort();
		}

		String rawQuery = uri.getRawQuery();

		if (rawQuery != null) {
			try {
				for (String queryPart : rawQuery.split("&")) {
					if (queryPart.contains("=")) {
						String[] split = queryPart.split("=");

						if (split.length != 2) {
							exception = new URISyntaxException(
									uri.toASCIIString(), "Query part " + queryPart + " contains to many =");
						} else {
							query.put(URLDecoder.decode(split[0], "UTF-8"), URLDecoder.decode(split[1], "UTF-8"));
						}
					} else {
						query.put(URLDecoder.decode(queryPart, "UTF-8"), null);
					}
				}
			} catch (UnsupportedEncodingException e) {
				exception = e;
				return (S) this;
			}
		}

		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S protocol(String protocol) {
		this.protocol = protocol;
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S host(String host) {
		this.host = host;
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S port(int port) {
		this.port = port;
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S path(String path) {
		this.path = path;
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S query(String key) {
		this.query.put(key, null);
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S query(String key, Object value) {
		this.query.put(key, value.toString());
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S query(Map<String, String> query) {
		this.query = query;
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S header(String key, String value) {
		this.headers.put(key, value);
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	public S headers(Map<String, String> headers) {
		this.headers = headers;
		return (S) this;
	}

	public void validate() throws HttpException {
		if (port == -2 && protocol != null) {
			if (protocol.equals("http")) {
				port = 80;
			} else if (protocol.equals("https")) {
				port = 443;
			} else {
				throw new HttpException("Failed to determine default port for protocol " + protocol);
			}
		}

		if (exception != null) {
			throw new HttpException("Cached internal exception", exception);
		} else if (client == null) {
			throw new HttpException("httpClient is null", new NullPointerException());
		} else if (protocol == null) {
			throw new HttpException("protocol is null", new NullPointerException());
		} else if (host == null) {
			throw new HttpException("host is null", new NullPointerException());
		} else if (path == null) {
			throw new HttpException("path is null", new NullPointerException());
		} else if (port < 1 || port > 65535) {
			throw new HttpException("port " + port + " is out of range");
		}
	}

	private String buildQuery() throws HttpException {
		StringBuilder builder = new StringBuilder();

		try {
			for (Map.Entry<String, String> entry : query.entrySet()) {
				builder
						.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
						.append("=")
						.append(URLEncoder.encode(entry.getValue(), "UTF-8"))
						.append("&");
			}
		} catch (UnsupportedEncodingException e) {
			// I'm not even going to question this...
			throw new HttpException("UTF-8 not supported", e);
		}

		if (builder.length() < 1) {
			return "";
		}

		// cut away last &
		return builder.substring(0, builder.length() - 1);
	}

	public URL buildUrl() throws HttpException {
		if (host.endsWith("/")) {
			host = host.substring(0, host.length() - 1);
		}

		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}

		try {
			return new URL(protocol, host, port, path + "?" + buildQuery());
		} catch (MalformedURLException e) {
			throw new HttpException("Failed to build url", e);
		}
	}

	public void applyHeaders(HttpURLConnection urlConnection) {
		headers.forEach(urlConnection::setRequestProperty);
	}
}
