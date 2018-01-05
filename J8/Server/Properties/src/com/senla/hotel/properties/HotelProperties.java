package com.senla.hotel.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.EmptyObjectException;

public class HotelProperties {
	private static HotelProperties instance;
	private static Logger logger;
	private Properties properties;
	static {
		logger = Logger.getLogger(HotelProperties.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	private HotelProperties(String path) throws IOException {
		FileInputStream read = new FileInputStream(path);
		properties = new Properties();
		properties.load(read);
		read.close();
	}

	public static HotelProperties getInstance(String path) throws IOException {
		try {
			if (instance == null) {
				instance = new HotelProperties(path);
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
