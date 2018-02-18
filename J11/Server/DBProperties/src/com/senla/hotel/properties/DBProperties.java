package com.senla.hotel.properties;
import com.senla.hotel.exceptions.EmptyObjectException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {
	private static DBProperties instance;
	private static Logger logger = LogManager.getLogger(DBProperties.class);
	private Properties properties;

	private DBProperties(String path) throws IOException {
		try (FileInputStream read = new FileInputStream(path);) {
			properties = new Properties();
			properties.load(read);
		} catch (IOException e) {
			logger.log(Level.DEBUG, e.getMessage());
			throw e;
		}
	}

	public static DBProperties getInstance(String path) throws IOException {
		try {
			if (instance == null) {
				instance = new DBProperties(path);
			}
			return instance;
		} catch (IOException e) {
			logger.log(Level.DEBUG, e.getMessage());
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
			logger.log(Level.DEBUG, e.getMessage());
			throw e;
		}
		return result;
	}

}
