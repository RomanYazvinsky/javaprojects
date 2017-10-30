package by.hotel.entities;

public class Room extends AEntity {
	private int number;
	private Client[] clients;
	private int capacity;
	private int clientNumber;
	private int star;
	private RoomStatus status;
	private int pricePerDay;

	public Room(int number, int capacity, int type, RoomStatus status, int pricePerDay) {
		super();
		this.number = number;
		this.capacity = capacity;
		this.star = type;
		this.status = status;
		this.pricePerDay = pricePerDay;
		clients = new Client[capacity];
		this.clientNumber = 0;
	}

	private int findClient(Client client) {
		for (int i = 0; i < clientNumber; i++) {
			if (clients[i].equals(client)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		Room other = (Room) obj;
		if (number != other.number)
			return false;
		return true;
	}

	public int addClient(Client client) {
		if (clientNumber == capacity) {
			return -1;
		}
		if (findClient(client) >= 0) {
			return 0;
		}
		clients[clientNumber++] = client;
		return 1;
	}

	public Boolean deleteClient(Client client) {
		int clientIndex = findClient(client);
		if (clientIndex < 0)
			return false;
		clients[clientIndex] = clients[--clientNumber];
		clients[clientNumber] = null;
		return true;
	}

	public int getPricePerDay() {
		return pricePerDay;
	}

	public int getCapacity() {
		return capacity;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	public int getNumber() {
		return number;
	}

	public int getStar() {
		return star;
	}

	public int getClientNumber() {
		return clientNumber;
	}

}
