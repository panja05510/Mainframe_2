package com.citizens.mainframe.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Accounts {

	public Accounts(String serviceName, String response, String error, Integer delay) {
		super();
		this.serviceName = serviceName;
		this.response = response;
		this.error = error;
		this.delay = delay;
	}

	private String serviceName;
	private String response;
	private String error;
	private Integer delay;

	@Override
	public String toString() {
		return "Accounts [serviceName=" + serviceName + ", response=" + response + ", error=" + error + ", delay="
				+ delay + "]";
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

}
