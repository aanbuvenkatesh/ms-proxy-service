package io.anbu.proxyservice.repository;

import io.anbu.proxyservice.config.ProxyConfiguration;
import io.anbu.proxyservice.entity.Client;

public interface ClientRepository {

	public static ClientRepository getConnection() {
		if (ProxyConfiguration.getDatabaseKey().equalsIgnoreCase("INMEMORY")) {
			return new ClientInMemoryRepository();
		} else if (ProxyConfiguration.getDatabaseKey().equalsIgnoreCase("MONGODB")) {
			return new ClientMongoRepository();
		}
		return new ClientInMemoryRepository();
	}

	public Client getClientData(String clientId);

	public void incrementRequestCount(String clientId);

	public void resetRequestCount(String clientId);

}
