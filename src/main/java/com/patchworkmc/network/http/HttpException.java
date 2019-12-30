package com.patchworkmc.network.http;

public class HttpException extends Exception {
	public HttpException(String msg) {
		super(msg);
	}

	public HttpException(String msg, Throwable t) {
		super(msg, t);
	}
}
