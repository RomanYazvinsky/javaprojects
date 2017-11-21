package com.senla.hotel.entities;

import java.io.Serializable;

import com.senla.hotel.exceptions.IncorrectNameException;

public class Client extends AEntity implements Serializable {
	private static final long serialVersionUID = 1676886118386345127L;
	private String name;

	public Client(String data) throws IncorrectNameException {
		super();
		if (data == null || data.equals("")) {
			throw new IncorrectNameException();
		}
		String[] params = data.split(",");
		if (params.length > 1) {
			id = Integer.parseInt(params[0].trim());
			name = params[1].trim();
		}else {
			name = params[0].trim();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IncorrectNameException {
		if (name == null || name.isEmpty()) {
			throw new IncorrectNameException();
		}
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + "," + name;
	}

}
