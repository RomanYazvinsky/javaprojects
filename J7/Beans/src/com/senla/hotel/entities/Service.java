package com.senla.hotel.entities;

import static com.senla.hotel.constants.Constants.logFileHandler;
import static com.senla.hotel.constants.Constants.serviceExportFile;
import static com.senla.hotel.constants.Constants.serviceHeaderCSV;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.PropertyType;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;

@CsvEntity(csvHeader = serviceHeaderCSV, filename = serviceExportFile, valueSeparator = ",", entityId = "id")
public class Service implements IEntity, Serializable {
	private static final long serialVersionUID = 4634029185600413436L;
	private static final int idColumn = 0;
	private static final int priceColumn = 1;
	private static final int nameColumn = 2;
	private static final int dateColumn = 3;
	private static Logger logger;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = idColumn)
	private Integer id;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = priceColumn)
	private Integer price;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = nameColumn)
	private String name;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = dateColumn, getterMethod = "getTime")
	private Date date;
	static {
		logger = Logger.getLogger(Service.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(logFileHandler);
	}

	public Service() {

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

	public Service(String data) {
		super();
		String[] serviceData = data.split(" ");
		try {
			id = Integer.parseInt(serviceData[idColumn]);
			price = Integer.parseInt(serviceData[priceColumn]);
			name = serviceData[nameColumn].trim();
			date = new Date(Long.parseLong(serviceData[dateColumn]));
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			logger.log(Level.SEVERE, e.getMessage());
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

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}
