package com.senla.hotel.entities;

import static com.senla.hotel.constants.Constants.roomExportFile;
import static com.senla.hotel.constants.Constants.roomHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.ID;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_CAPACITY;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_NUMBER;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_PRICE;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_STAR;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_STATUS;

import java.io.Serializable;

import com.senla.hotel.annotations.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.hotel.constants.PropertyType;
import com.senla.hotel.constants.RoomStatus;

@Table(tableName = "rooms")
@CsvEntity(csvHeader = roomHeaderCSV, filename = roomExportFile, valueSeparator = ",", entityId = "id")
public class Room implements IEntity, Serializable, Cloneable {
	private static final long serialVersionUID = 6938497574865077752L;

	private static Logger logger = LogManager.getLogger(Room.class);

	@Id
	@Column(fieldName = "id", isString = false)
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ID)
	private Integer id;
	@Column(fieldName = "number", isString = false)
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_NUMBER)
	private Integer number;
	@Column(fieldName = "capacity", isString = false)
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_CAPACITY)
	private Integer capacity;
	@Column(fieldName = "star", isString = false)
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_STAR)
	private Integer star;
	@Column(fieldName = "status", isString = true)
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_STATUS)
	private RoomStatus status;
	@Column(fieldName = "room_price", isString = false)
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ROOM_PRICE)
	private Integer pricePerDay;

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
		if (id != null && other.id != null) {
			if (id.equals(other.id)) {
				return true;
			}
		}
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
		room.id = null;
		room.number = null;
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
			logger.log(Level.DEBUG, e.getMessage());
		}
	}

}
