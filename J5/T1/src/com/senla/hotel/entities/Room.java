package com.senla.hotel.entities;

import java.util.HashSet;

import utilities.RoomStatusParser;

public class Room extends AEntity {
	private Integer capacity;
	private Integer star;
	private RoomStatus status;
	private Integer pricePerDay;
	private HashSet<Integer> clientIDs;

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
		clientIDs = new HashSet<>();
	}

	public Room(String data) {
		super();
		String[] roomData = data.split(" ");
		if (roomData.length > 4) {
			clientIDs = new HashSet<>();
			id = Integer.parseInt(roomData[0]);
			capacity = Integer.parseInt(roomData[1]);
			star = Integer.parseInt(roomData[2]);
			status = RoomStatusParser.define(roomData[3]);
			pricePerDay = Integer.parseInt(roomData[4]);
			if (roomData.length > 5) {
				for (int i = 0; i < roomData.length - 5 && i < capacity; i++) {
					if (roomData[i + 5] != null) {
						clientIDs.add(Integer.parseInt(roomData[i + 5]));
					}
				}
			}
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
		Room other = (Room) obj;
		if (id != other.getID())
			return false;
		return true;
	}

	public Boolean addClient(Integer id) {
		Boolean result = clientIDs.add(id);
		if (result) {
			if (clientIDs.size() == capacity) {
				status = RoomStatus.USED;
			} else {
				status = RoomStatus.PARTIALLY_FREE;
			}
		}
		return result;
	}

	public Boolean deleteClient(Integer id) {
		if (status == RoomStatus.ONSERVICE) {
			return false;
		}
		Boolean result = clientIDs.remove(id);
		if (result) {
			if (clientIDs.size() == 0) {
				status = RoomStatus.FREE;
			} else {
				status = RoomStatus.PARTIALLY_FREE;
			}
		}
		return result;
	}

	public Integer[] getClientIDs() {
		return (Integer[]) clientIDs.toArray();
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
			clientIDs.clear();
		}
	}

	public Integer getStar() {
		return star;
	}

	public Integer getClientCount() {
		return clientIDs.size();
	}
	
	public Boolean isOnService() {
		return status.equals(RoomStatus.ONSERVICE);
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
