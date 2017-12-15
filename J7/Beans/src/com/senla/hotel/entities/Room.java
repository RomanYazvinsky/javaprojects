package com.senla.hotel.entities;

import static com.senla.hotel.constants.Constants.logFileHandler;
import static com.senla.hotel.constants.Constants.roomExportFile;
import static com.senla.hotel.constants.Constants.roomHeaderCSV;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.PropertyType;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.exceptions.IncorrectParameterException;

@CsvEntity(csvHeader = roomHeaderCSV, filename = roomExportFile, valueSeparator = ",", entityId = "id")
public class Room implements IEntity, Serializable, Cloneable {
	private static final long serialVersionUID = 6938497574865077752L;
	private static final int idColumn = 0;
	private static final int numberColumn = 1;
	private static final int capacityColumn = 2;
	private static final int starColumn = 3;
	private static final int statusColumn = 4;
	private static final int pricePerDayColumn = 5;
	private static Logger logger;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = idColumn)
	private Integer id;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = numberColumn)
	private Integer number;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = capacityColumn)
	private Integer capacity;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = starColumn)
	private Integer star;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = statusColumn)
	private RoomStatus status;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = pricePerDayColumn)
	private Integer pricePerDay;
	static {
		logger = Logger.getLogger(Room.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(logFileHandler);
	}

	public Room() {

	}

	public Room(int number, int capacity, int star, int pricePerDay) throws IncorrectParameterException {
		super();
		if (number < 1 || capacity <= 0 || star <= 0 || pricePerDay <= 0) {
			logger.log(Level.SEVERE, "incorrect parameters");
			throw new IncorrectParameterException();
		}
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

	public Room(String data) {
		String[] roomData = data.split(",");
		try {
			id = Integer.parseInt(roomData[idColumn]);
			star = Integer.parseInt(roomData[starColumn]);
			capacity = Integer.parseInt(roomData[capacityColumn]);
			status = RoomStatus.valueOf(roomData[statusColumn]);
			pricePerDay = Integer.parseInt(roomData[pricePerDayColumn]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}

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
		room.status = RoomStatus.FREE_NOW;
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

}
