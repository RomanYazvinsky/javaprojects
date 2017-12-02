package com.senla.hotel.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.EmptyObjectException;

public class HotelProperties {
	private static final String PATH_TO_PROPERTIES = "../Properties/hotel.properties";
	private static HotelProperties instance;
	private static Logger logger;
	private Properties properties;
	private FileInputStream read;
	static {
		logger = Logger.getLogger(HotelProperties.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	private HotelProperties() throws IOException {
		properties = new Properties();
		read = new FileInputStream(PATH_TO_PROPERTIES);
		properties.load(read);
	}

	public static HotelProperties getInstance() throws IOException {
		try {
			if (instance == null) {
				instance = new HotelProperties();
			}
			return instance;
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public String getProperty(String key) throws EmptyObjectException {
		if (properties == null) {
			throw new EmptyObjectException();
		}
		String result = properties.getProperty(key);
		if (result == null) {
			EmptyObjectException e = new EmptyObjectException();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
		return result;
	}
}
