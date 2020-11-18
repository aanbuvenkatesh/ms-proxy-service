package io.anbu.proxyservice.integration;

import java.io.IOException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import io.anbu.proxyservice.config.ProxyConfiguration;

@Component
public class ApacheHttpClient implements HttpRequestHandler {

	@Override
	public HttpResponse httpPost(String url, Map<String, String> headers, String requestBody) {
		HttpResponse httpResponse = new HttpResponse();
		try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(getHttpConfig())
				.build()) {
			HttpPost httpPost = new HttpPost(url);
			addHeaderToRequest(httpPost, headers);
			StringEntity stringEntity = new StringEntity(requestBody);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			httpResponse.setHeaders(transformHeader(response.getAllHeaders()));
			httpResponse.setResponse(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		return httpResponse;
	}

	@Override
	public HttpResponse httpPut(String url, Map<String, String> headers, String requestBody) {
		HttpResponse httpResponse = new HttpResponse();
		try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(getHttpConfig())
				.build()) {
			HttpPut httpPut = new HttpPut(url);
			addHeaderToRequest(httpPut, headers);
			StringEntity stringEntity = new StringEntity(requestBody);
			httpPut.setEntity(stringEntity);
			CloseableHttpResponse response = httpclient.execute(httpPut);
			httpResponse.setHeaders(transformHeader(response.getAllHeaders()));
			httpResponse.setResponse(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		return httpResponse;
	}

	@Override
	public HttpResponse httpGet(String url, Map<String, String> headers) {
		HttpResponse httpResponse = new HttpResponse();
		try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(getHttpConfig())
				.build()) {
			HttpGet httpGet = new HttpGet(url);
			addHeaderToRequest(httpGet, headers);
			CloseableHttpResponse response = httpclient.execute(httpGet);
			httpResponse.setHeaders(transformHeader(response.getAllHeaders()));
			httpResponse.setResponse(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		return httpResponse;
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
