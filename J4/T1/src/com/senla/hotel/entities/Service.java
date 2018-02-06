package com.senla.hotel.entities;

import java.util.Date;
import java.util.GregorianCalendar;

import utilities.IDGenerator;

public class Service extends AEntity {
	private Integer price;
	private String name;
	private GregorianCalendar date;

	public Service(Integer id, int price, String name, GregorianCalendar date) {
		super(id);
		if (price <= 0) {
			this.price = 1;
		} else {
			this.price = price;

		}
		if (name.contains(" ")) {
			this.name = name.split(" ")[0];
		} else {
			this.name = name;
		}
		this.date = date;
		IDGenerator.addServiceID(id);
	}

	public Service(String data) {
		super();
		String[] serviceData = data.split(" ");
		if (serviceData.length == 4) {
			id = Integer.parseInt(serviceData[0]);
			price = Integer.parseInt(serviceData[1]);
			name = serviceData[2];
			date = new GregorianCalendar();
			date.setTime(new Date(Long.parseLong(serviceData[3])));
		}
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(int price) {
		if (price > 0) {
			this.price = price;
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		Service other = (Service) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (id == null) {
			if (other.getID() != null)
				return false;
		} else if (!id.equals(other.getID()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + " " + price + " " + name + " " + date.getTimeInMillis();
	}

}
