package com.senla.hotel.constants;

import com.senla.hotel.entities.*;
public class Constants {
	public static final String clientDataDir = "../Data/Client/";
	public static final String orderDataDir = "../Data/Order/";
	public static final String serviceDataDir = "../Data/Service/";
	public static final String roomDataDir = "../Data/Room/";
	
	public static final String extension = ".csv";

	public static final String roomHeaderCSV = "id, capacity, star, status, price per day, client ids\n";
	public static final String clientHeaderCSV = " id, name\n";
	public static final String orderHeaderCSV = "id, room ID, client ID, from, to, services \n";
	public static final String serviceHeaderCSV = " id, price, name, date\n";

	public static String getPath(Room room) {
		return roomDataDir + room.getID() + extension;
	}

	public static String getPath(Client client) {
		return clientDataDir + client.getID() + extension;
	}

	public static String getPath(Service service) {
		return serviceDataDir + service.getID() + extension;
	}

	public static String getPath(Order order) {
		return orderDataDir + order.getID() + extension;
	}

}
