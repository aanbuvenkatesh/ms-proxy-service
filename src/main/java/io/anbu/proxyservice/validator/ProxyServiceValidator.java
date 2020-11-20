package io.anbu.proxyservice.validator;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import io.anbu.proxyservice.constants.HttpRequestType;
import io.anbu.proxyservice.constants.ResponseMessageHub;
import io.anbu.proxyservice.view.ProxyServiceView;

/**
 * Validations for proxy service.
 * 
 * @author aanbuvenkatesh
 */
public class ProxyServiceValidator {

	private ProxyServiceValidator() {

	}

	private static final String REGEX_URL = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)"
			+ "[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

	public static void validateProxyInput(ProxyServiceView requestPayload) {
		validateURL(requestPayload.getUrl());
		validateRequestType(requestPayload.getRequestType());
		validateRequestBody(requestPayload.getRequestType(), requestPayload.getRequestBody());
	}

	private static void validateRequestType(HttpRequestType httpRequestType) {
		if (httpRequestType == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					ResponseMessageHub.MISSING_FIELD_DATA.getMessage(ProxyServiceView.FIELD_HTTP_REQUEST_TYPE));
		}
	}

	private static void validateRequestBody(HttpRequestType requestType, String requestBody) {
		if ((requestType == HttpRequestType.POST || requestType == HttpRequestType.PUT) && isEmptyField(requestBody)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					ResponseMessageHub.REQUEST_BODY_MANDATORY.getMessage());
		}
	}

	private static void validateURL(String url) {
		if (isEmptyField(url)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					ResponseMessageHub.MISSING_FIELD_DATA.getMessage(ProxyServiceView.FIELD_URL));
		} else if (!url.matches(REGEX_URL)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					ResponseMessageHub.INVALID_URI_PROVIDED.getMessage(url));
		} else if (!url.substring(0, 5).equalsIgnoreCase("https")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					ResponseMessageHub.INVALID_URI_HTTP.getMessage(url));
		}
	}

	private static boolean isEmptyField(String text) {
		return text == null || text.isEmpty() || text.trim().length() == 0;
	}

}
