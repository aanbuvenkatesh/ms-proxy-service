package io.anbu.proxyservice.repository;

import io.anbu.proxyservice.config.ProxyConfiguration;
import io.anbu.proxyservice.entity.Client;

/**
 * Client repository to manage the connection.
 * 
 * @author aanbuvenkatesh
 */
public interface ClientRepository {

	/**
	 * Based on the configuration returns the appropriate repository connection.
	 * 
	 * @return Repository Object
	 */
	public static ClientRepository getConnection() {
		if (ProxyConfiguration.getDatabaseKey().equalsIgnoreCase("INMEMORY")) {
			return new ClientInMemoryRepository();
		} else if (ProxyConfiguration.getDatabaseKey().equalsIgnoreCase("MONGODB")) {
			return new ClientMongoRepository();
		}
		return new ClientInMemoryRepository();
	}

	/**
	 * Get the client data for the client id
	 * 
	 * @param clientId Client Id
	 * @return Client
	 */
	public Client getClientData(String clientId);

	/**
	 * Increment the request count for the client
	 * 
	 * @param clientId Client Id
	 */
	public void incrementRequestCount(String clientId);

	/**
	 * Reset the request count for the client after the expiry.
	 * 
	 * @param clientId Client Id
	 */
	public void resetRequestCount(String clientId);

}
