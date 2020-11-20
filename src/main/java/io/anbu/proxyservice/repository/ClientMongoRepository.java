package io.anbu.proxyservice.repository;

import java.util.Date;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import io.anbu.proxyservice.config.MongoConfiguration;
import io.anbu.proxyservice.entity.Client;

/**
 * Mongo repository for the client entity
 * 
 * @author aanbuvenkatesh
 */
public class ClientMongoRepository implements ClientRepository {

	private MongoConfiguration mongoConfig;
	private MongoTemplate mongoTemplate;

	public ClientMongoRepository() {
		mongoConfig = new MongoConfiguration();
		mongoTemplate = mongoConfig.mongoTemplate();
	}

	@Override
	public Client getClientData(String clientId) {
		return mongoTemplate.findById(clientId, Client.class);
	}

	@Override
	@Transactional
	public void incrementRequestCount(String clientId) {
		Client client = mongoTemplate.findById(clientId, Client.class);
		if (client == null) {
			client = new Client();
			client.setClientId(clientId);
			client.setLastResetTime(new Date());
			client.setRequestCount(1);
		} else {
			client.setRequestCount(client.getRequestCount() + 1);
		}
		mongoTemplate.save(client);
	}

	@Override
	@Transactional
	public void resetRequestCount(String clientId) {
		Client client = mongoTemplate.findById(clientId, Client.class);
		client.setRequestCount(1);
		client.setLastResetTime(new Date());
		mongoTemplate.save(client);
	}

}
