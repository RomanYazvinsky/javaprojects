package com.senla.hotel.entities;

import java.util.Date;

import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;

public class Service extends AEntity {
	private Integer price;
	private String name;
	private Date date;

	public Service( int price, String name, Date date)
			throws IncorrectIDEcxeption, IncorrectParameterException {
		super();
		if (price <= 0) {
			throw new IncorrectParameterException();
		}
		this.price = price;
		if (name.contains(" ")) {
			this.name = name.split(" ")[0];
		} else {
			this.name = name;
		}
		this.date = date;
	}

	public Service(String data) throws NumberFormatException, ArrayIndexOutOfBoundsException, IncorrectIDEcxeption {
		super();
		String[] serviceData = data.split(" ");
		if (serviceData.length == 4) {
			price = Integer.parseInt(serviceData[1]);
			name = serviceData[2];
			date = new Date();
			date.setTime(Long.parseLong(serviceData[3]));
		}
	}

	public Date getDate() {
		return date;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(int price) throws IncorrectParameterException {
		if (price < 0) {
			throw new IncorrectParameterException();
		}
		this.price = price;
		
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
		return true;
	}

	@Override
	public String toString() {
		return id + " " + price + " " + name + " " + date.getTime();
	}

}
