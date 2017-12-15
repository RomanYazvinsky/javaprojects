package com.senla.hotel.entities;

import static com.senla.hotel.constants.Constants.clientExportFile;
import static com.senla.hotel.constants.Constants.clientHeaderCSV;
import static com.senla.hotel.constants.Constants.logFileHandler;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.PropertyType;
import com.senla.hotel.exceptions.IncorrectNameException;

@CsvEntity(csvHeader = clientHeaderCSV, filename = clientExportFile, valueSeparator = ",", entityId = "getId")
public class Client implements Serializable, IEntity {
	private static final long serialVersionUID = 1676886118386345127L;
	private static final int idColumn = 0;
	private static final int nameColumn = 1;
	private static Logger logger;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = idColumn)
	private Integer id;
	@CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = nameColumn)
	private String name;
	static {
		logger = Logger.getLogger(Client.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(logFileHandler);
	}

	public Client() {

	}

	public Client(String name) {
		super();
		if (name == null || name.isEmpty()) {
			logger.log(Level.SEVERE, "incorrect name");
		} else {
			String[] params = name.split(",");
			if (params.length > 1) {
				try {
					this.id = Integer.parseInt(params[idColumn]);
					this.name = params[nameColumn].trim();
				} catch (SecurityException | NumberFormatException e) {
					logger.log(Level.SEVERE, "incorrect name");
				}
			} else {
				this.name = name;
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IncorrectNameException {
		if (name == null || name.isEmpty()) {
			logger.log(Level.SEVERE, "incorrect name");
			throw new IncorrectNameException();
		}
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
		return id + "," + name;
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
