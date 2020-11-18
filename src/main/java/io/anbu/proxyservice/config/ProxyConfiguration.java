package io.anbu.proxyservice.config;

public class ProxyConfiguration {

	private ProxyConfiguration() {

	}

	public static final String PROPERTY_PREFIX = "io.anbu.proxy.";

	private static String getProperty(ConfigurationProperty property) {
		String value = System.getenv(PROPERTY_PREFIX + property.getProperty());
		if (value == null) {
			value = System.getProperty(PROPERTY_PREFIX + property.getProperty());
		}
		return value == null || value.isEmpty() ? property.getValue() : value;
	}

	public static Integer getHttpTimeOut() {
		return Integer.parseInt(getProperty(ConfigurationProperty.TIMEOUT_SECONDS));
	}

	public static Integer getUserRequestLimit() {
		return Integer.parseInt(getProperty(ConfigurationProperty.USER_LIMIT));
	}

	public static Integer getLimitResetTime() {
		return Integer.parseInt(getProperty(ConfigurationProperty.LIMIT_RESET_MINUTES));
	}

	public static String getDatabaseName() {
		return getProperty(ConfigurationProperty.DATABASE_NAME);
	}

	public static String getDatabaseKey() {
		return getProperty(ConfigurationProperty.DATABASE_KEY);
	}

	public static String getDatabaseConnection() {
		return getProperty(ConfigurationProperty.DATABASE_CONNECTION);
	}
}
