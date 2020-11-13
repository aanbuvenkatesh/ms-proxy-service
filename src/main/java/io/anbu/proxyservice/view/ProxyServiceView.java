package io.anbu.proxyservice.view;

import java.util.Map;

import io.anbu.proxyservice.constants.HttpRequestType;

public class ProxyServiceView {

	public static final String FIELD_URL = "url";
	public static final String FIELD_HEADER = "header";
	public static final String FIELD_HTTP_REQUEST_TYPE = "requestType";
	public static final String FIELD_REQUEST_BODY = "requestBody";

	private String url;
	private Map<String, String> headers;
	private HttpRequestType requestType;
	private String requestBody;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public HttpRequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(HttpRequestType requestType) {
		this.requestType = requestType;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

}
