package com.senla.hotel.entities;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.IncorrectNameException;

public class Client extends AEntity implements Serializable {
	private static final long serialVersionUID = 1676886118386345127L;
	private static Logger logger;
	private String name;
	static {
		logger = Logger.getLogger(Client.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	public Client(String name) throws IncorrectNameException {
		super();
		if (name == null || name.isEmpty()) {
			logger.log(Level.SEVERE, "incorrect name");
			throw new IncorrectNameException();
		}
		this.name = name;
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

}
