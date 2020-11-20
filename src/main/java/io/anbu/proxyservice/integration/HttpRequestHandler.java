package io.anbu.proxyservice.integration;

import java.util.Map;

import io.anbu.proxyservice.constants.HttpRequestType;

/**
 * HTTP request handler for proxy service.
 * 
 * @author aanbuvenkatesh
 */
public interface HttpRequestHandler {

	public static final String ERROR_PREFIX = "[HOST] ";

	public HttpResponse executeRequest(HttpRequestType requestType, String url, Map<String, String> headers,
			String requestBody);
}
