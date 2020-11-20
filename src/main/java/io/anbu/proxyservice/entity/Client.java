package io.anbu.proxyservice.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class for Client data
 * 
 * @author aanbuvenkatesh
 */
@Document
public class Client {

	@Id
	private String clientId;
	private Integer requestCount;
	private Date lastResetTime;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
	}

	public Date getLastResetTime() {
		return lastResetTime;
	}

	public void setLastResetTime(Date lastResetTime) {
		this.lastResetTime = lastResetTime;
	}

}
