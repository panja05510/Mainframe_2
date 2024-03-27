package com.citizens.mainframe.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Accounts {
	private String id;
	private String serviceName;
	private Integer delay;
	private Map<String, String> response;

	@Override
	public String toString() {
		return "Accounts [id=" + id + ", serviceName=" + serviceName + ", delay=" + delay + ", response=" + response
				+ "]";
	}

	public Accounts(String id, String serviceName, Integer delay, Map<String, String> response) {
		super();
		this.id = id;
		this.serviceName = serviceName;
		this.delay = delay;
		this.response = response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Map<String, String> getResponse() {
		return response;
	}

	public void setResponse(Map<String, String> response) {
		this.response = response;
	}

}
