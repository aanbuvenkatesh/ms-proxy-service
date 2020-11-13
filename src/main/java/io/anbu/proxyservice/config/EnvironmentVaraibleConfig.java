package io.anbu.proxyservice.config;

import org.springframework.stereotype.Component;

@Component
public class EnvironmentVaraibleConfig implements ProxyConfiguration {

	private String getProperty(String property, String defaultValue) {
		String value = System.getenv(PROPERTY_PREFIX + property);
		if (value == null) {
			value = System.getProperty(PROPERTY_PREFIX + property);
		}
		return value == null || value.isEmpty() ? defaultValue : value;
	}

	@Override
	public Integer getHttpTimeOut() {
		return Integer.parseInt(getProperty("timeout.seconds", "5"));
	}

	@Override
	public Integer getUserRequestLimit() {
		return Integer.parseInt(getProperty("user.limit", "50"));
	}

	@Override
	public Integer getLimitResetTime() {
		return Integer.parseInt(getProperty("limit.reset.minutes", "1"));
	}

}
