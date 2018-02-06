package com.senla.hotel.constants;

import java.io.IOException;
import java.util.logging.FileHandler;


public class Constants {
	public static final String PATH_TO_PROPERTIES = "../Properties/hotel.properties";
	public static final String clientDataDir = "Data/Client/";
	public static final String orderDataDir = "Data/Order/";
	public static final String serviceDataDir = "Data/Service/";
	public static final String roomDataDir = "Data/Room/";
	
	public static final String clientExportFile = "Data/Client/clients.csv";
	public static final String orderExportFile = "Data/Order/orders.csv";
	public static final String serviceExportFile = "Data/Service/services.csv";
	public static final String roomExportFile = "Data/Room/rooms.csv";
	
	public static final String extensionCSV = ".csv";

	public static final String roomHeaderCSV = "id, number, capacity, star, status, price per day";
	public static final String clientHeaderCSV = "id, name";
	public static final String orderHeaderCSV = "id, room ID, client ID, from, to, services";
	public static final String serviceHeaderCSV = "id, price, name, date";
	
	public static FileHandler LOGFILE_HANDLER;
	
	static {
		try {
			LOGFILE_HANDLER = new FileHandler("log.xml");
		} catch (SecurityException | IOException e) {
			System.out.println("Logfile unreachable. Please, close the progrem");
		}
	}
	
}
