package com.senla.hotel.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.hotel.exceptions.EmptyObjectException;

public class HotelProperties {
	private static HotelProperties instance;
	private static Logger logger = LogManager.getLogger(HotelProperties.class);
	private Properties properties;

	private HotelProperties(String path) throws IOException {
		try (FileInputStream read = new FileInputStream(path);) {
			properties = new Properties();
			properties.load(read);
		} catch (IOException e) {
			logger.log(Level.DEBUG, e.getMessage());
			File file = new File(path + ".test");
			System.out.println(file.getAbsolutePath());
			throw e;
		}
	}

	public static HotelProperties getInstance(String path) throws IOException {
		try {
			if (instance == null) {
				instance = new HotelProperties(path);
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
			File file = new File("testfile.txt");
			logger.log(Level.DEBUG, e.getMessage());
			throw e;
		}
		return result;
	}

}
