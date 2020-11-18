package io.anbu.proxyservice.config;

public enum ConfigurationProperty {

	TIMEOUT_SECONDS("timeout.seconds", "5"), 
	USER_LIMIT("user.limit", "50"),
	LIMIT_RESET_MINUTES("limit.reset.minutes", "1"),
	
	DATABASE_KEY("db.key", "INMEMORY"),
	DATABASE_NAME("db.name", ""),
	DATABASE_CONNECTION("db.connection", "");
	
	private final String property;
	private final String value;

	ConfigurationProperty(String property, String value) {
		this.property = property;
		this.value = value;
	}

	public String getProperty() {
		return this.property;
	}

	public String getValue() {
		return this.value;
	}
}
