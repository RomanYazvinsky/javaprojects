package com.senla.hotel.properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.senla.hotel.exceptions.NullException;
public class HotelProperties {
	private static final String PATH_TO_PROPERTIES = "../Properties/hotel.properties";
	private Properties properties;
	private FileInputStream read;
	private static HotelProperties instance;

	private HotelProperties() throws IOException {
		properties = new Properties();
		read  = new FileInputStream(PATH_TO_PROPERTIES);
		properties.load(read);
	}
	
	public static HotelProperties getInstance() throws IOException {
		if (instance == null) {
			instance = new HotelProperties();
		}
		return instance;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	
	public String getProperty(String key) throws NullException {	
		if (properties == null) {
			throw new NullException();
		}
		String result = properties.getProperty(key);
		if (result == null) {
			throw new NullException();
		}
		return result;
	}
}
