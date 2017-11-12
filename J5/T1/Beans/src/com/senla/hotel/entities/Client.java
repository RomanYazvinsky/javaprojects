package com.senla.hotel.entities;

public class Client extends AEntity {
	private String name;

	public Client(Integer id, String name) {
		super(id);
		if (name.contains(" ")) {
			this.name = name.split(" ")[0];
		} else {
			this.name = name;
		}
	}

	public Client(String data) {
		super();
		String[] clientData = data.split(" ");
		if (clientData.length == 2) {
			name = clientData[0];
			id = Integer.parseInt(clientData[1]);
		}
	}

	public String getName() {
		return name;
	}

	public void setString(String name) {
		if (!name.isEmpty()) {
			this.name = name;
		}
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
		return true;
	}

	@Override
	public String toString() {
		return name + " " + id;
	}


}
