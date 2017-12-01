package com.senla.hotel.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;

public class Service extends AEntity implements Serializable {
	private static final long serialVersionUID = 4634029185600413436L;
	private static Logger logger;
	private Integer price;
	private String name;
	private Date date;
	static {
		logger = Logger.getLogger(Service.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	
	public Service(int price, String name, Date date) throws IncorrectIDEcxeption, IncorrectParameterException {
		super();
		if (price <= 0) {
			logger.log(Level.SEVERE, "incorrect price");
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

	public Date getDate() {
		return date;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(int price) throws IncorrectParameterException {
		if (price < 0) {
			logger.log(Level.SEVERE, "setPrice");
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
		return true;
	}

	@Override
	public String toString() {
		return id + ", " + price + ", " + name + ", " + date.getTime();
	}

}
