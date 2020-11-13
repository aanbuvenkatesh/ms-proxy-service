package io.anbu.proxyservice.config;

public interface ProxyConfiguration {

	public static String PROPERTY_PREFIX = "io.anbu.proxy.";

	Integer getHttpTimeOut();

	Integer getUserRequestLimit();

	Integer getLimitResetTime();

}
