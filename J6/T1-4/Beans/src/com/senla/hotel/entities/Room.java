package com.senla.hotel.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;

public class Room extends AEntity implements Serializable, Cloneable {
	private static final long serialVersionUID = 6938497574865077752L;
	private Integer capacity;
	private Integer star;
	private RoomStatus status;
	private Integer pricePerDay;
	private HashSet<Integer> clientIDs;

	public Room(int capacity, int star, RoomStatus status, int pricePerDay) throws IncorrectParameterException {
		super();
		if (capacity <= 0 || star <= 0 || pricePerDay <= 0) {
			throw new IncorrectParameterException();
		}
		this.capacity = capacity;
		this.star = star;
		this.status = status;
		this.pricePerDay = pricePerDay;
		clientIDs = new HashSet<>();
	}

	public Room(String data) throws ArrayIndexOutOfBoundsException, NumberFormatException, IncorrectParameterException {
		super();
		String[] roomData = data.split(",");
		if (roomData.length > 4) {
			clientIDs = new HashSet<>();
			capacity = Integer.parseInt(roomData[1].trim());
			star = Integer.parseInt(roomData[2].trim());
			status = RoomStatus.valueOf(roomData[3].trim());
			pricePerDay = Integer.parseInt(roomData[4].trim());
			if (pricePerDay <= 0) {
				throw new IncorrectParameterException();
			}
			if (roomData.length > 5) {
				for (int i = 0; i < roomData.length - 5 && i < capacity; i++) {
					if (roomData[i + 5] != null) {
						clientIDs.add(Integer.parseInt(roomData[i + 5].trim()));
					}
				}
			}
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id);
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
		return true;
	}

	public Boolean addClient(Integer id) throws IncorrectIDEcxeption {
		if (id < 0) {
			throw new IncorrectIDEcxeption();
		}
		if (status == RoomStatus.USED_NOW || status == RoomStatus.ONSERVICE_NOW) {
			return false;
		}
		Boolean result = clientIDs.add(id);
		if (result) {
			if (clientIDs.size() == capacity) {
				status = RoomStatus.USED_NOW;
			} else {
				status = RoomStatus.PARTIALLY_FREE_NOW;
			}
		}
		return result;
	}

	public Boolean deleteClient(Integer id) {
		if (status == RoomStatus.ONSERVICE_NOW) {
			return false;
		}
		Boolean result = clientIDs.remove(id);
		if (result) {
			if (clientIDs.size() == 0) {
				status = RoomStatus.FREE_NOW;
			} else {
				status = RoomStatus.PARTIALLY_FREE_NOW;
			}
		}
		return result;
	}

	public ArrayList<Integer> getClientIDs() {
		if (status == RoomStatus.ONSERVICE_NOW) {
			return new ArrayList<Integer>();
		}
		return new ArrayList<Integer>(clientIDs);
	}

	public Integer getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(int pricePerDay) throws IncorrectParameterException {
		if (pricePerDay <= 0) {
			throw new IncorrectParameterException();
		}
		this.pricePerDay = pricePerDay;

	}

	public Integer getCapacity() {
		return capacity;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	public Integer getStar() {
		return star;
	}

	public Integer getClientCount() {
		return clientIDs.size();
	}

	public Boolean isOnService() {
		return status.equals(RoomStatus.ONSERVICE_NOW);
	}

	private String getClientIDString() {
		if (clientIDs == null) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (Integer i : clientIDs) {
			result.append(", ").append(i);
		}
		return result.toString();
	}

	@Override
	public String toString() {
		return id + ", " + capacity + ", " + star + ", " + status + ", " + pricePerDay + getClientIDString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Room room;
		try {
			room = new Room(capacity, star, status, pricePerDay);
			return room;

		} catch (IncorrectParameterException e) {}
		return null;
	}

}
