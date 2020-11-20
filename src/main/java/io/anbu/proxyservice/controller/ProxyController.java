package io.anbu.proxyservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.anbu.proxyservice.integration.HttpResponse;
import io.anbu.proxyservice.service.ProxyService;
import io.anbu.proxyservice.view.ProxyServiceView;

/**
 * Rest Controller for the Proxy service.
 * 
 * @author aanbuvenkatesh
 */
@RestController
public class ProxyController {

	@Autowired
	private ProxyService proxyService;

	@PostMapping(path = "/v1.0.0/proxy/serve/client/{clientId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> proxyService(@PathVariable String clientId,
			@RequestBody ProxyServiceView requestPayload) {
		Logger logger = LoggerFactory.getLogger(ProxyController.class);
		logger.debug(clientId + " has initated the request to the proxy service");
		HttpResponse proxyResponse = proxyService.invoke(clientId, requestPayload);
		logger.debug(clientId + " is served successfully");
		return ResponseEntity.ok().headers(proxyResponse.getHeaders()).body(proxyResponse.getResponse());
	}
}
