package com.senla.hotel.entities;

import static com.senla.hotel.constants.Constants.LOGFILE_HANDLER;
import static com.senla.hotel.constants.Constants.orderExportFile;
import static com.senla.hotel.constants.Constants.orderHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.ID;
import static com.senla.hotel.constants.EntityColumnOrder.ORDER_CLIENT;
import static com.senla.hotel.constants.EntityColumnOrder.ORDER_FROM;
import static com.senla.hotel.constants.EntityColumnOrder.ORDER_ROOM;
import static com.senla.hotel.constants.EntityColumnOrder.ORDER_SERVICES;
import static com.senla.hotel.constants.EntityColumnOrder.ORDER_TO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.PropertyType;

@CsvEntity(csvHeader = orderHeaderCSV, filename = orderExportFile, valueSeparator = ",", entityId = "id")

public class Order implements Serializable, IEntity {
	private static final long serialVersionUID = -4420394137579611748L;

	private static Logger logger;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ID)
	private Integer id;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = ORDER_ROOM, setterMethod = "setId", getterMethod = "getId")
	private Room room;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = ORDER_CLIENT, setterMethod = "setId", getterMethod = "getId")
	private Client client;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, getterMethod = "getTime", setterMethod = "setTime", columnNumber = ORDER_FROM)
	private Date orderFrom;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, getterMethod = "getTime", setterMethod = "setTime", columnNumber = ORDER_TO)
	private Date orderTo;
	@CsvProperty(propertyType = PropertyType.COLLECTION_PROPERTY, setterMethod = "setId", getterMethod = "getId", columnNumber = ORDER_SERVICES, storagingClass = "com.senla.hotel.entities.Service")
	private ArrayList<Service> services;
	static {
		logger = Logger.getLogger(Order.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(LOGFILE_HANDLER);
	}

	public Order() {

	}

	public Order(Room room, Client client, Date orderFrom, Date orderTo, ArrayList<Service> services) {
		super();
		this.room = room;
		this.client = client;
		this.orderFrom = orderFrom;
		this.orderTo = orderTo;
		if (services == null) {
			this.services = new ArrayList<>();
		} else {
			this.services = services;
		}
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
		this.orderTo = orderTo;
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

	public void setId(String id) {
		try {
			this.id = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

}
