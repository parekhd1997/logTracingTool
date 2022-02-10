package com.test.logtracing.pojo;

public class Log {
	
	String id;
	String state;
	String type;
	String host;
	long timestamp;
	boolean alert;
	
	public Log(String id, String state, String type, String host, long timestamp) {
		super();
		this.id = id;
		this.state = state;
		this.type = type;
		this.host = host;
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean getAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", state=" + state + ", type=" + type + ", host=" + host + ", timestamp=" + timestamp
				+ ", Alert=" + alert + "]";
	}
	
	public void raiseAlertIfTookLonger(Log start) {
		//TODO: check if finish before start then throw exception
		long timeGap = this.getTimestamp() - start.getTimestamp();
		this.setAlert(timeGap > 4 ? true : false);
		start.setAlert(timeGap > 4 ? true : false);
	}
}
