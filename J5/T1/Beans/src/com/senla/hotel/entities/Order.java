package com.senla.hotel.entities;

import java.util.ArrayList;
import java.util.Date;

import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;

public class Order extends AEntity {
	private Integer roomID;
	private Integer clientID;
	private Date orderFrom;
	private Date orderTo;
	private ArrayList<Service> services;

	public Order(Integer roomID, Integer clientID, Date orderFrom, Date orderTo,
			ArrayList<Service> services) throws IncorrectIDEcxeption, IncorrectParameterException {
		super();
		if (roomID == null || roomID < 0 || orderFrom == null || orderTo == null) {
			throw new IncorrectParameterException();
		}
		this.roomID = roomID;
		this.clientID = clientID;
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

	public Order(String data) throws ArrayIndexOutOfBoundsException, NumberFormatException, IncorrectIDEcxeption {
		super();
		String[] orderData = data.split(" ");
		if (orderData.length > 4) {
			services = new ArrayList<Service>();
			roomID = Integer.parseInt(orderData[1]);
			clientID = Integer.parseInt(orderData[2]);
			orderFrom = new Date();
			orderFrom.setTime(Long.parseLong(orderData[3]));
			orderTo = new Date();
			orderTo.setTime(Long.parseLong(orderData[4]));
			if (orderData.length > 7)
				for (int i = 0; i < orderData.length - 5;) {
					services.add(new Service(orderData[i++ + 5] + " " + orderData[i++ + 5] + " " + orderData[i++ + 5]
							+ " " + orderData[i++ + 5]));
				}
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

	public Integer getRoomID() {
		return roomID;
	}

	public Integer getClientID() {
		return clientID;
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
		result = prime * result + ((clientID == null) ? 0 : clientID.hashCode());
		result = prime * result + ((orderFrom == null) ? 0 : orderFrom.hashCode());
		result = prime * result + ((orderTo == null) ? 0 : orderTo.hashCode());
		result = prime * result + ((roomID == null) ? 0 : roomID.hashCode());
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
		if (clientID == null) {
			if (other.clientID != null)
				return false;
		} else if (!clientID.equals(other.clientID))
			return false;
		if (compareDates((Order) obj) != 0) {
			return false;
		}
		if (roomID == null) {
			if (other.roomID != null)
				return false;
		} else if (!roomID.equals(other.roomID))
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
				result.append(" ").append(service.toString());
		}
		return result.toString();
	}

	@Override
	public String toString() {
		return id + " " + roomID + " " + clientID + " " + orderFrom.getTime() + " " + orderTo.getTime()
				+ getServicesString();
	}
}
