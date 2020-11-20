package io.anbu.proxyservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import io.anbu.proxyservice.constants.HttpRequestType;
import io.anbu.proxyservice.controller.ProxyController;
import io.anbu.proxyservice.view.ProxyServiceView;

/**
 * Basic test cases for proxy service.
 * 
 * @author aanbuvenkatesh
 */
@SpringBootTest
class ProxyServiceApplicationTests {

	@Autowired
	private ProxyController controller;

	@Test
	void shouldSucceedForValidRequest() {
		ProxyServiceView requestPayload = new ProxyServiceView();
		requestPayload.setUrl("https://www.google.com");
		requestPayload.setRequestType(HttpRequestType.GET);
		ResponseEntity<?> response = controller.proxyService("TEST", requestPayload);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void shouldThrowExceptionForMissingRequestBody() {
		ProxyServiceView requestPayload = new ProxyServiceView();
		requestPayload.setUrl("https://www.google.com");
		requestPayload.setRequestType(HttpRequestType.POST);
		assertThrows(ResponseStatusException.class, () -> controller.proxyService("TEST", requestPayload));
	}

	@Test
	void shouldThrowExceptionIncaseOfInvalidHost() {
		ProxyServiceView requestPayload = new ProxyServiceView();
		requestPayload.setUrl("https://www.google1.com");
		requestPayload.setRequestType(HttpRequestType.POST);
		assertThrows(ResponseStatusException.class, () -> controller.proxyService("TEST", requestPayload));
	}

	@Test
	void shouldThrowExceptionForNonHttpsURL() {
		ProxyServiceView requestPayload = new ProxyServiceView();
		requestPayload.setUrl("http://www.google.com");
		requestPayload.setRequestType(HttpRequestType.GET);
		assertThrows(ResponseStatusException.class, () -> controller.proxyService("TEST", requestPayload));
	}

}
