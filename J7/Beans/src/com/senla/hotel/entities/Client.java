package com.senla.hotel.entities;

import static com.senla.hotel.constants.Constants.LOGFILE_HANDLER;
import static com.senla.hotel.constants.Constants.clientExportFile;
import static com.senla.hotel.constants.Constants.clientHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.ID;
import static com.senla.hotel.constants.EntityColumnOrder.NAME;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.PropertyType;

@CsvEntity(csvHeader = clientHeaderCSV, filename = clientExportFile, valueSeparator = ",", entityId = "getId")
public class Client implements Serializable, IEntity {
	private static final long serialVersionUID = 1676886118386345127L;
	private static Logger logger;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ID)
	private Integer id;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = NAME)
	private String name;
	static {
		logger = Logger.getLogger(Client.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(LOGFILE_HANDLER);
	}

	public Client() {

	}

	public Client(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Client other = (Client) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + ", " + name;
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
