package com.senla.hotel.entities;

import static com.senla.hotel.constants.Constants.LOGFILE_HANDLER;
import static com.senla.hotel.constants.Constants.roomExportFile;
import static com.senla.hotel.constants.Constants.roomHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.*;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.PropertyType;
import com.senla.hotel.constants.RoomStatus;

@CsvEntity(csvHeader = roomHeaderCSV, filename = roomExportFile, valueSeparator = ",", entityId = "id")
public class Room implements IEntity, Serializable, Cloneable {
	private static final long serialVersionUID = 6938497574865077752L;

	private static Logger logger;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ID)
	private Integer id;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_NUMBER)
	private Integer number;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_CAPACITY)
	private Integer capacity;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_STAR)
	private Integer star;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_STATUS)
	private RoomStatus status;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_PRICE)
	private Integer pricePerDay;
	static {
		logger = Logger.getLogger(Room.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(LOGFILE_HANDLER);
	}

	public Room() {

	}

	public Room(int number, int capacity, int star, int pricePerDay) {
		super();
		this.number = number;
		this.capacity = capacity;
		this.star = star;
		this.pricePerDay = pricePerDay;
		this.status = RoomStatus.FREE_NOW;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	public Integer getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(int pricePerDay) {
		this.pricePerDay = pricePerDay;

	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;

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

	public void setStar(int star) {
		this.star = star;

	}

	public Boolean isOnService() {
		return status.equals(RoomStatus.ONSERVICE_NOW);
	}

	@Override
	public String toString() {
		return id + ", " + number + ", " + capacity + ", " + star + ", " + status + ", " + pricePerDay;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Room room;
		room = (Room) super.clone();
		return room;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setId(String id) {
		try {
			this.id = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

}
