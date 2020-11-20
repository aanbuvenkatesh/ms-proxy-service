package io.anbu.proxyservice.repository;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import io.anbu.proxyservice.entity.Client;

/**
 * In memory client repository for client data.
 * 
 * @author aanbuvenkatesh
 */
public class ClientInMemoryRepository implements ClientRepository {

	private static ConcurrentHashMap<String, Client> clientDataStore = new ConcurrentHashMap<>();

	public Client getClientData(String clientId) {
		return clientDataStore.get(clientId);
	}

	public void incrementRequestCount(String clientId) {
		synchronized (clientDataStore) {
			if (clientDataStore.get(clientId) == null) {
				Client client = new Client();
				client.setLastResetTime(new Date());
				client.setRequestCount(1);
				clientDataStore.put(clientId, client);
			} else {
				clientDataStore.get(clientId).setRequestCount(clientDataStore.get(clientId).getRequestCount() + 1);
			}
		}
	}

	public void resetRequestCount(String clientId) {
		synchronized (clientDataStore) {
			clientDataStore.get(clientId).setLastResetTime(new Date());
			clientDataStore.get(clientId).setRequestCount(1);
		}
	}

}
