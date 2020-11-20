package io.anbu.proxyservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Entry point of the Proxy application
 * 
 * @author aanbuvenkatesh
 */
@SpringBootApplication
@EnableAutoConfiguration
public class ProxyServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ProxyServiceApplication.class);
		logger.info("Starting up the proxy service");
		SpringApplication.run(ProxyServiceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ProxyServiceApplication.class);
	}
}
