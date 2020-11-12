package io.anbu.proxyservice.config;

import org.springframework.stereotype.Component;

@Component
public class EnvironmentVaraibleConfig implements ProxyConfiguration {

	private String getProperty(String property, String defaultValue) {
		String value = System.getProperty(PROPERTY_PREFIX + property);
		return value == null || value.isEmpty() ? defaultValue : value;
	}

	@Override
	public Integer getTimeOut() {
		return Integer.parseInt(getProperty("user.limit", "1"));
	}

	@Override
	public Integer getUserRequestLimit() {
		return Integer.parseInt(getProperty("timeout.seconds", "5"));
	}

	@Override
	public Integer getRequestsForMinute() {
		return Integer.parseInt(getProperty("limit.reset.minutes", "1"));
	}

}
