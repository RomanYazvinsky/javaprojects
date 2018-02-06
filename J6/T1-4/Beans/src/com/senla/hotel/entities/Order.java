package com.senla.hotel.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;

public class Order extends AEntity  implements Serializable{
	private static final long serialVersionUID = -4420394137579611748L;
	private static Logger logger;
	private Integer roomId;
	private Integer clientId;
	private Date orderFrom;
	private Date orderTo;
	private ArrayList<Service> services;
	static {
		logger = Logger.getLogger(Order.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}


	public Order(Integer roomID, Integer clientID, Date orderFrom, Date orderTo,
			ArrayList<Service> services) throws IncorrectIDEcxeption, IncorrectParameterException {
		super();
		if (roomID == null || roomID < 0 || orderFrom == null || orderTo == null) {
			logger.log(Level.SEVERE, "incorrect parameters");
			throw new IncorrectParameterException();
		}
		this.roomId = roomID;
		this.clientId = clientID;
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

	public Integer getRoomId() {
		return roomId;
	}

	public Integer getClientId() {
		return clientId;
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
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((orderFrom == null) ? 0 : orderFrom.hashCode());
		result = prime * result + ((orderTo == null) ? 0 : orderTo.hashCode());
		result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
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
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (compareDates((Order) obj) != 0) {
			return false;
		}
		if (roomId == null) {
			if (other.roomId != null)
				return false;
		} else if (!roomId.equals(other.roomId))
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
		return id + ", " + roomId + ", " + clientId + ", " + orderFrom.getTime() + ", " + orderTo.getTime()
				+ getServicesString();
	}
}
