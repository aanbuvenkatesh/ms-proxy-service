package io.anbu.proxyservice.repository;

import io.anbu.proxyservice.entity.Client;

public interface ClientRepository {

	public Client getClientData(String clientId);

	public void incrementRequestCount(String clientId);

	public void resetRequestCount(String clientId);

	public void createClient(String clientId);

}
