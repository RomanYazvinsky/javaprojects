package com.senla.hotel.entities;

import static com.senla.hotel.constants.Constants.LOGFILE_HANDLER;
import static com.senla.hotel.constants.Constants.serviceExportFile;
import static com.senla.hotel.constants.Constants.serviceHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.*;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.PropertyType;

@CsvEntity(csvHeader = serviceHeaderCSV, filename = serviceExportFile, valueSeparator = ",", entityId = "id")
public class Service implements IEntity, Serializable {
	private static final long serialVersionUID = 4634029185600413436L;
	private static Logger logger;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ID)
	private Integer id;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = SERVICE_PRICE)
	private Integer price;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = NAME)
	private String name;
	@CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = SERVICE_DATE, getterMethod = "getTime")
	private Date date;
	static {
		logger = Logger.getLogger(Service.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(LOGFILE_HANDLER);
	}

	public Service() {

	}

	public Service(int price, String name, Date date) {
		super();
		this.price = price;
		this.name = name;
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(int price) {

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

	public void setId(String id) {
		try {
			this.id = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

}
