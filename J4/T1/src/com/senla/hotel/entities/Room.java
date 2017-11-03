package com.senla.hotel.entities;

import com.senla.hotel.utilities.ArrayWorker;
import com.senla.hotel.utilities.IDGenerator;
import com.senla.hotel.utilities.RoomStatusParser;

public class Room extends AEntity {
	private Integer capacity;
	private Integer star;
	private RoomStatus status;
	private Integer pricePerDay;
	private Integer[] clientIDs;

	public Room(Integer id, int capacity, int star, RoomStatus status, int pricePerDay) {
		super(id);
		if (capacity <= 0) {
			this.capacity = 1;
		} else {
			this.capacity = capacity;
		}
		if (star <= 0) {
			this.star = 1;
		} else {
			this.star = star;
		}
		this.status = status;
		if (pricePerDay <= 0) {
			this.pricePerDay = 1;
		} else {
			this.pricePerDay = pricePerDay;
		}
		clientIDs = new Integer[capacity];
		IDGenerator.addRoomID(id);
	}

	public Room(String data) {
		super();
		String[] roomData = data.split(" ");
		if (roomData.length > 4) {
			id = Integer.parseInt(roomData[0]);
			star = Integer.parseInt(roomData[2]);
			capacity = Integer.parseInt(roomData[1]);
			status = RoomStatusParser.define(roomData[3]);
			pricePerDay = Integer.parseInt(roomData[4]);
			if (roomData.length > 5) {
				clientIDs = new Integer[capacity];
				for (int i = 0; i < roomData.length - 5; i++) {
					if (roomData[i + 5].compareTo("null") == 0) {
						clientIDs[i] = null;
					} else {
						clientIDs[i] = Integer.parseInt(roomData[i + 5]);
					}
				}
			}
		}

	}

	private int findClient(Integer id) {
		for (int i = 0; i < ArrayWorker.getCount(clientIDs); i++) {
			if (clientIDs[i].equals(id)) {
				return i;
			}
		}
		return -1;
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
		Room other = (Room) obj;
		if (id != other.getID())
			return false;
		return true;
	}

	public int addClient(Integer id) {
		int clientNumber = ArrayWorker.getCount(clientIDs);
		if (clientNumber == capacity || findClient(id) >= 0 || status == RoomStatus.ONSERVICE) {
			return -1;
		}
		clientIDs[clientNumber] = id;
		if (clientNumber + 1 == capacity) {
			status = RoomStatus.USED;
		} else {
			status = RoomStatus.PARTIALLY_FREE;
		}
		return 1;
	}

	public Boolean deleteClient(Integer id) {
		if (status == RoomStatus.ONSERVICE) {
			return false;
		}
		int clientNumber = ArrayWorker.getCount(clientIDs);
		int clientIndex = findClient(id);
		if (clientIndex < 0) {
			return false;
		}
		clientIDs[clientIndex] = clientIDs[--clientNumber];
		clientIDs[clientNumber] = null;
		if (clientNumber == 0) {
			status = RoomStatus.FREE;
		} else {
			status = RoomStatus.PARTIALLY_FREE;
		}
		return true;
	}

	public Integer[] getClientIDs() {
		return clientIDs;
	}

	public Integer getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(int pricePerDay) {
		if (pricePerDay > 0) {
			this.pricePerDay = pricePerDay;
		}
	}

	public Integer getCapacity() {
		return capacity;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
		if (status == RoomStatus.ONSERVICE) {
			for (int i = 0; i < capacity; i++) {
				clientIDs[i] = null;
			}
		}
	}

	public Integer getStar() {
		return star;
	}

	public Integer getClientCount() {
		return ArrayWorker.getCount(clientIDs);
	}

	private String getClientIDString() {
		if (clientIDs == null) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (Integer i : clientIDs) {
			result.append(" ").append(i);
		}
		return result.toString();
	}

	@Override
	public String toString() {
		return id + " " + capacity + " " + star + " " + status + " " + pricePerDay + getClientIDString();
	}

}
