package io.anbu.proxyservice.integration;

import java.util.Map;

public interface HttpRequestHandler {

	public HttpResponse httpPost(String url, Map<String, String> headers, String requestBody);

	public HttpResponse httpPut(String url, Map<String, String> headers, String requestBody);

	public HttpResponse httpGet(String url, Map<String, String> headers);
}
