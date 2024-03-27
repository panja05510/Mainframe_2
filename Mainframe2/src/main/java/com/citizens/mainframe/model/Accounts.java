package com.citizens.mainframe.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Accounts {
	private String id;
	private String serviceName;
	private Integer delay;

	public Accounts(String id, String serviceName, Integer delay, Map<Integer, String> response) {
		super();
		this.id = id;
		this.serviceName = serviceName;
		this.delay = delay;
		this.response = response;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	private Map<Integer, String> response;
	/*
	 * "90982": "some error fixed lenght response 01",
	 * "87348":"some error fixed lenght resonse 02 "
	 */

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

	public Map<Integer, String> getResponse() {
		return response;
	}

	public void setResponse(Map<Integer, String> response) {
		this.response = response;
	}

	
	
	@Override
	public String toString() {
		return "Accounts [id=" + id + ", serviceName=" + serviceName + ", delay=" + delay + ", response=" + response
				+ "]";
	}

}
