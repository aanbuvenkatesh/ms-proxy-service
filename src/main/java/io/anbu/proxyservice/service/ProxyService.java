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

	private ClientRepository clientRepository;

	@Autowired
	private HttpRequestHandler httpRequest;

	public ProxyService() {
		clientRepository = ClientRepository.getConnection();
	}

	public HttpResponse invoke(String clientId, ProxyServiceView requestPayload) {
		processClient(clientId);
		ProxyServiceValidator.validateProxyInput(requestPayload);
		return executeRequest(clientId, requestPayload);
	}

	private void processClient(String clientId) {
		if (clientRepository.getClientData(clientId) == null) {
			clientRepository.incrementRequestCount(clientId);
		} else if (getIntervalFromLastReset(clientId) >= ProxyConfiguration.getLimitResetTime()) {
			clientRepository.resetRequestCount(clientId);
		} else if (getIntervalFromLastReset(clientId) < ProxyConfiguration.getLimitResetTime() && clientRepository
				.getClientData(clientId).getRequestCount().equals(ProxyConfiguration.getUserRequestLimit())) {
			throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, ResponseMessageHub.THERSHOLD_LIMT_EXCEEDED
					.getMessage(String.valueOf(ProxyConfiguration.getUserRequestLimit())));
		} else if (getIntervalFromLastReset(clientId) <= ProxyConfiguration.getLimitResetTime() && clientRepository
				.getClientData(clientId).getRequestCount() < ProxyConfiguration.getUserRequestLimit()) {
			clientRepository.incrementRequestCount(clientId);
		}
	}

	private double getIntervalFromLastReset(String clientId) {
		return new java.util.Date().getTime() / (1000 * 60)
				- clientRepository.getClientData(clientId).getLastResetTime().getTime() / (1000 * 60);
	}

	private HttpResponse executeRequest(String clientId, ProxyServiceView requestPayload) {
		HttpResponse httpResponse = httpRequest.executeRequest(requestPayload.getRequestType(), requestPayload.getUrl(),
				requestPayload.getHeaders(), requestPayload.getRequestBody());
		appendHeaders(httpResponse, clientId);
		return httpResponse;
	}

	private void appendHeaders(HttpResponse httpResponse, String clientId) {
		httpResponse.getHeaders().add("x-proxy-usage-limit", String.valueOf(ProxyConfiguration.getUserRequestLimit()));
		httpResponse.getHeaders().add("x-proxy-usage-count",
				clientRepository.getClientData(clientId).getRequestCount().toString());
		httpResponse.getHeaders().add("x-proxy-reset-minutes", String.valueOf(ProxyConfiguration.getLimitResetTime()));
	}

}
