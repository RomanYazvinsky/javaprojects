package com.senla.hotel.entities;

import static com.senla.hotel.constants.Constants.logFileHandler;
import static com.senla.hotel.constants.Constants.orderDataDir;
import static com.senla.hotel.constants.Constants.orderExportFile;
import static com.senla.hotel.constants.Constants.orderHeaderCSV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.PropertyType;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;

@CsvEntity(csvHeader = orderHeaderCSV, filename = orderDataDir + orderExportFile, valueSeparator = ",", entityId = "id")

public class Order implements Serializable, IEntity {
	private static final long serialVersionUID = -4420394137579611748L;
	private static final int idColumn = 0;
	private static final int roomIdColumn = 1;
	private static final int clientIdColumn = 2;
	private static final int orderFromColumn = 3;
	private static final int orderToColumn = 4;
	private static final int servicesIdColumn = 5;
	private static Logger logger;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = idColumn)
	private Integer id;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = roomIdColumn)
	private Room room;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = clientIdColumn)
	private Client client;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, getterMethod = "getTime", columnNumber = orderFromColumn)
	private Date orderFrom;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, getterMethod = "getTime", columnNumber = orderToColumn)
	private Date orderTo;
	@CsvProperty(propertyType = PropertyType.COLLECTION_PROPERTY, getterMethod = "getId", columnNumber = servicesIdColumn)
	private ArrayList<Service> services;
	static {
		logger = Logger.getLogger(Order.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(logFileHandler);
	}

	public Order() {

	}

	public Order(Room room, Client client, Date orderFrom, Date orderTo, ArrayList<Service> services)
			throws IncorrectIDEcxeption, IncorrectParameterException {
		super();
		if (room == null || client == null || orderFrom == null || orderTo == null) {
			logger.log(Level.SEVERE, "incorrect parameters");
			throw new IncorrectParameterException();
		}
		this.room = room;
		this.client = client;
		if (orderFrom.before(orderTo)) {
			this.orderFrom = orderFrom;
			this.orderTo = orderTo;
		} else {
			this.orderFrom = orderTo;
			this.orderTo = orderFrom;
		}
		if (services == null) {
			this.services = new ArrayList<>();
		} else {
			this.services = services;
		}
	}

	public Order(String data) {
		super();
		String[] orderData = data.split(",");
		if (orderData.length > 4) {
			services = new ArrayList<>();
			room = new Room();
			client = new Client();
			try {
				id = Integer.parseInt(orderData[idColumn]);
				room.setId(Integer.parseInt(orderData[roomIdColumn]));
				client.setId(Integer.parseInt(orderData[clientIdColumn]));
				orderFrom = new Date(Long.parseLong(orderData[orderFromColumn]));
				orderTo = new Date(Long.parseLong(orderData[orderToColumn]));
				for (int i = servicesIdColumn; i < orderData.length; i++) {
					Service service = new Service();
					services.add(service);
					service.setId(Integer.parseInt(orderData[i]));
				}

			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		} else {
			logger.log(Level.SEVERE, "Incorrect data");
		}
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void addService(Service service) {
		services.add(service);
	}

	public Integer getServiceCount() {
		return services.size();
	}

	public Date getOrderTo() {
		return orderTo;
	}

	public Room getRoom() {
		return room;
	}

	public Client getClient() {
		return client;
	}

	public Date getOrderFrom() {
		return orderFrom;
	}

	public void setOrderTo(Date orderTo) {
		if (orderFrom.before(orderTo)) {
			this.orderTo = orderTo;
		}
	}

	public Boolean isActive(Date now) {
		if (orderFrom.before(now) && orderTo.after(now)) {
			return true;
		}
		return false;
	}

	public Integer compareDates(Order other) {
		if (orderTo.before(other.getOrderFrom())) {
			return -1;
		}
		if (orderFrom.after(other.getOrderTo())) {
			return 1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((orderFrom == null) ? 0 : orderFrom.hashCode());
		result = prime * result + ((orderTo == null) ? 0 : orderTo.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
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
		Order other = (Order) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (compareDates((Order) obj) != 0) {
			return false;
		}
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}

	private String getServicesString() {
		if (services == null) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (Service service : services) {
			if (service != null)
				result.append(", ").append(service.toString());
		}
		return result.toString();
	}

	@Override
	public String toString() {
		return id + ", " + room.getId() + ", " + client.getId() + ", " + orderFrom.getTime() + ", " + orderTo.getTime()
				+ getServicesString();
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
