package io.anbu.proxyservice.constants;

/**
 * Central Hub to manage the response messages in Proxy application
 * 
 * @author aanbuvenkatesh
 */
public enum ResponseMessageHub {

	THERSHOLD_LIMT_EXCEEDED("You have exceeded a maximum of %s connection in a minute"),
	MISSING_FIELD_DATA("Field %s should not be null or empty"),
	INVALID_URI_PROVIDED("Invalid URL [%s]. Unable to process the request. A Valid URL may be in the format https://www.example.com"),
	INVALID_URI_HTTP("Invalid URL [%s] - Only HTTPS connections are allowed"),
	REQUEST_BODY_MANDATORY("Request Body is required for POST and PUT requests"),
	HTTP_REQUEST_FAILED("Failed to initiate a HTTP request");

	private final String message;

	ResponseMessageHub(String message) {
		this.message = message;
	}

	public String getMessage(String... params) {
		return String.format(this.message, (Object[]) params);
	}
}
