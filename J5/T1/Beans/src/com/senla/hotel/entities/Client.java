package com.senla.hotel.entities;

import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectNameException;

public class Client extends AEntity {
	private String name;

	public Client(Integer id, String name) throws NullPointerException, IncorrectIDEcxeption {
		super(id);
		if (name.contains(" ")) {
			this.name = name.split(" ")[0];
		} else {
			this.name = name;
		}
	}

	public Client(String data) throws NumberFormatException, ArrayIndexOutOfBoundsException {
		super();
		String[] clientData = data.split(" ");
		try {
			name = clientData[0];
			id = Integer.parseInt(clientData[1]);
		}catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw e;
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
		if (id != other.id)
			return false;
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
