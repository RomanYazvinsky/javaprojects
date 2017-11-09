package com.senla.hotel.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Order extends AEntity {
	private Integer roomID;
	private Integer clientID;
	private GregorianCalendar orderFrom;
	private GregorianCalendar orderTo;
	private ArrayList<Service> services;

	public Order(Integer id, Integer roomID, Integer clientID, GregorianCalendar orderFrom, GregorianCalendar orderTo,
			ArrayList<Service> services) {
		super(id);
		this.roomID = roomID;
		this.clientID = clientID;
		if (orderFrom.before(orderTo)) {
			this.orderFrom = orderFrom;
			this.orderTo = orderTo;
		} else {
			this.orderFrom = orderTo;
			this.orderTo = orderFrom;
		}
		this.services = services;
	}

	public Order(String data) {
		super();
		String[] orderData = data.split(" ");
		if (orderData.length > 4) {
			services = new ArrayList<Service>();
			id = Integer.parseInt(orderData[0]);
			roomID = Integer.parseInt(orderData[1]);
			clientID = Integer.parseInt(orderData[2]);
			orderFrom = new GregorianCalendar();
			orderFrom.setTime(new Date(Long.parseLong(orderData[3])));
			orderTo = new GregorianCalendar();
			orderTo.setTime(new Date(Long.parseLong(orderData[4])));
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

	public void addService(ArrayList<Service> additionalServices) {
		services.addAll(additionalServices);
	}

	public Integer getServiceCount() {
		return services.size();
	}

	public GregorianCalendar getOrderTo() {
		return orderTo;
	}

	public Integer getRoomID() {
		return roomID;
	}

	public Integer getClientID() {
		return clientID;
	}

	public GregorianCalendar getOrderFrom() {
		return orderFrom;
	}

	public void setOrderTo(GregorianCalendar orderTo) {
		if (orderFrom.before(orderTo)) {
			this.orderTo = orderTo;
		}
	}

	public Boolean isActive(GregorianCalendar now) {
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
		return id + " " + roomID + " " + clientID + " " + orderFrom.getTimeInMillis() + " " + orderTo.getTimeInMillis()
				+ getServicesString();
	}
}
