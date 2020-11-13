package io.anbu.proxyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.anbu.proxyservice.config.ProxyConfiguration;
import io.anbu.proxyservice.constants.ResponseMessageHub;
import io.anbu.proxyservice.integration.HttpRequestHandler;
import io.anbu.proxyservice.integration.HttpResponse;
import io.anbu.proxyservice.repository.ClientRepository;
import io.anbu.proxyservice.validator.ProxyServiceValidator;
import io.anbu.proxyservice.view.ProxyServiceView;

@Service(value = BeanDefinition.SCOPE_PROTOTYPE)
public class ProxyService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private HttpRequestHandler httpRequest;

	@Autowired
	private ProxyConfiguration configuration;

	public HttpResponse invoke(String clientId, ProxyServiceView requestPayload) {
		processClient(clientId);
		ProxyServiceValidator.validateProxyInput(requestPayload);
		return executeRequest(requestPayload);
	}

	private void processClient(String clientId) {
		if (clientRepository.getClientData(clientId) == null) {
			clientRepository.createClient(clientId);
		} else if (getIntervalFromLastReset(clientId) >= configuration.getLimitResetTime()) {
			clientRepository.resetRequestCount(clientId);
		} else if (getIntervalFromLastReset(clientId) < configuration.getLimitResetTime() && clientRepository
				.getClientData(clientId).getRequestCount().equals(configuration.getUserRequestLimit())) {
			throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, ResponseMessageHub.THERSHOLD_LIMT_EXCEEDED
					.getMessage(String.valueOf(configuration.getUserRequestLimit())));
		} else if (getIntervalFromLastReset(clientId) <= configuration.getLimitResetTime()
				&& clientRepository.getClientData(clientId).getRequestCount() < configuration.getUserRequestLimit()) {
			clientRepository.incrementRequestCount(clientId);
		}
	}

	private double getIntervalFromLastReset(String clientId) {
		return new java.util.Date().getTime() / (1000 * 60)
				- clientRepository.getClientData(clientId).getLastResetTime().getTime() / (1000 * 60);
	}

	private HttpResponse executeRequest(ProxyServiceView requestPayload) {
		HttpResponse httpResponse = new HttpResponse();
		switch (requestPayload.getRequestType()) {
		case GET:
			httpResponse = httpRequest.httpGet(requestPayload.getUrl(), requestPayload.getHeaders());
			break;
		case POST:
			httpResponse = httpRequest.httpPost(requestPayload.getUrl(), requestPayload.getHeaders(),
					requestPayload.getRequestBody());
			break;
		case PUT:
			httpResponse = httpRequest.httpPut(requestPayload.getUrl(), requestPayload.getHeaders(),
					requestPayload.getRequestBody());
			break;
		default:
			break;
		}
		return httpResponse;
	}

}
