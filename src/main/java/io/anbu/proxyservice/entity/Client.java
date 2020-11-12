package io.anbu.proxyservice.entity;

import java.util.Date;

public class Client {

	private Integer requestCount;
	private Date lastResetTime;

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
