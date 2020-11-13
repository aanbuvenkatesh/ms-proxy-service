package io.anbu.proxyservice.integration;

import org.springframework.http.HttpHeaders;

public class HttpResponse {

	private HttpHeaders headers;

	private Object response;

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
