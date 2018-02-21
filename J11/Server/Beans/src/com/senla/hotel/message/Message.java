package com.senla.hotel.message;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 3679472022091623754L;
	private String command;
	private Object[] data;
	private boolean error;

	public Message(String command) {
		super();
		this.command = command;
		this.data = null;
	}

	public Message(String command, Object[] data) {
		super();
		this.command = command;
		this.data = data;
		error = false;
	}

	public Message(String command, boolean error) {
		this.command = command;
		this.error = error;
	}

	public Boolean getException() {
		return error;
	}

	public void setException(Boolean error) {
		this.error = error;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}
}
