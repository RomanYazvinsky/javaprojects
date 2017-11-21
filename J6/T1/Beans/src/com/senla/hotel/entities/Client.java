package com.senla.hotel.entities;

import com.senla.hotel.exceptions.IncorrectNameException;

public class Client extends AEntity {
	private String name;

	public Client(String data) throws IncorrectNameException {
		super();
		if (data == null || data.equals("")) {
			throw new IncorrectNameException();
		}
		name = data.split(" ")[0];
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
		result = prime * result + id;
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
		if (!name.equals(other.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name + " " + id;
	}

}
