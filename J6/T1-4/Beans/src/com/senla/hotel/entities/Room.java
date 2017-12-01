package com.senla.hotel.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;

public class Room extends AEntity implements Serializable, Cloneable {
	private static final long serialVersionUID = 6938497574865077752L;
	private static Logger logger;
	private Integer capacity;
	private Integer star;
	private RoomStatus status;
	private Integer pricePerDay;
	private HashSet<Integer> clientIds;
	static {
		logger = Logger.getLogger(Room.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}


	public Room(int capacity, int star, RoomStatus status, int pricePerDay) throws IncorrectParameterException {
		super();
		if (capacity <= 0 || star <= 0 || pricePerDay <= 0) {
			logger.log(Level.SEVERE, "incorrect parameters");
			throw new IncorrectParameterException();
		}
		this.capacity = capacity;
		this.star = star;
		this.status = status;
		this.pricePerDay = pricePerDay;
		clientIds = new HashSet<>();
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
			logger.log(Level.SEVERE, "addClient");
			throw new IncorrectIDEcxeption();
		}
		if (status == RoomStatus.USED_NOW || status == RoomStatus.ONSERVICE_NOW) {
			return false;
		}
		Boolean result = clientIds.add(id);
		if (result) {
			if (clientIds.size() == capacity) {
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
		Boolean result = clientIds.remove(id);
		if (result) {
			if (clientIds.size() == 0) {
				status = RoomStatus.FREE_NOW;
			} else {
				status = RoomStatus.PARTIALLY_FREE_NOW;
			}
		}
		return result;
	}

	public ArrayList<Integer> getClientIds() {
		if (status == RoomStatus.ONSERVICE_NOW) {
			return new ArrayList<Integer>();
		}
		return new ArrayList<Integer>(clientIds);
	}

	public Integer getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(int pricePerDay) throws IncorrectParameterException {
		if (pricePerDay <= 0) {
			logger.log(Level.SEVERE, "setPricePerDay");
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
		return clientIds.size();
	}

	public Boolean isOnService() {
		return status.equals(RoomStatus.ONSERVICE_NOW);
	}

	private String getClientIdString() {
		if (clientIds == null) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (Integer i : clientIds) {
			result.append(", ").append(i);
		}
		return result.toString();
	}

	@Override
	public String toString() {
		return id + ", " + capacity + ", " + star + ", " + status + ", " + pricePerDay + getClientIdString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Room room;
		room = (Room) super.clone();
		room.clientIds = new HashSet<>();
		room.status = RoomStatus.FREE_NOW;
		return room;
	}

}
