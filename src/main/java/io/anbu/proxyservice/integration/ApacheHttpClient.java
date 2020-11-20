package io.anbu.proxyservice.integration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import io.anbu.proxyservice.config.ProxyConfiguration;
import io.anbu.proxyservice.constants.HttpRequestType;

/**
 * Apache HTTP client for handling the http requests for the proxy service
 * 
 * @author aanbuvenkatesh
 */
@Component
public class ApacheHttpClient implements HttpRequestHandler {

	@Override
	public HttpResponse executeRequest(HttpRequestType requestType, String url, Map<String, String> headers,
			String requestBody) {
		HttpResponse httpResponse = new HttpResponse();
		try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(getHttpConfig())
				.build()) {
			HttpRequestBase httpRequest = getHttpClient(requestType, url, requestBody);
			addHeaderToRequest(httpRequest, headers);
			CloseableHttpResponse response = httpclient.execute(httpRequest);
			httpResponse.setHeaders(transformHeader(response.getAllHeaders()));
			httpResponse.setResponse(response.getEntity() == null ? "" : EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PREFIX + e.getMessage(), e);
		}
		return httpResponse;
	}

	private HttpRequestBase getHttpClient(HttpRequestType requestType, String url, String requestBody)
			throws UnsupportedEncodingException {
		HttpRequestBase httpClient = null;
		StringEntity stringEntity = null;
		switch (requestType) {
		case GET:
			httpClient = new HttpGet(url);
			break;
		case POST:
			httpClient = new HttpPost(url);
			stringEntity = new StringEntity(requestBody);
			((HttpPost) httpClient).setEntity(stringEntity);
			break;
		case PUT:
			httpClient = new HttpPut(url);
			stringEntity = new StringEntity(requestBody);
			((HttpPut) httpClient).setEntity(stringEntity);
			break;
		case DELETE:
			httpClient = new HttpDelete(url);
			break;
		case HEAD:
			httpClient = new HttpHead(url);
			break;
		case OPTIONS:
			httpClient = new HttpOptions(url);
			break;
		case TRACE:
			httpClient = new HttpTrace(url);
			break;
		default:
			break;
		}
		return httpClient;
	}

	private HttpHeaders transformHeader(Header[] allHeaders) {
		HttpHeaders httpHeaders = new HttpHeaders();
		for (Header header : allHeaders) {
			httpHeaders.set(header.getName(), header.getValue());
		}
		return httpHeaders;
	}

	private void addHeaderToRequest(HttpRequestBase request, Map<String, String> headers) {
		if (headers == null)
			return;
		for (Map.Entry<String, String> header : headers.entrySet()) {
			request.addHeader(header.getKey(), header.getValue());
		}
	}

	private RequestConfig getHttpConfig() {
		RequestConfig.Builder config = RequestConfig.custom();
		config.setConnectTimeout(ProxyConfiguration.getHttpTimeOut() * 1000);
		config.setConnectionRequestTimeout(ProxyConfiguration.getHttpTimeOut() * 1000);
		return config.build();
	}

}
