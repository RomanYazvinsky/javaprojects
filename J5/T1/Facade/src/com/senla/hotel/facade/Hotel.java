package com.senla.hotel.facade;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.senla.hotel.entities.Room;

public class Hotel {
	private static Hotel instance;
	private Facade facade;
	
	private Hotel() {
		facade = new Facade();
	}
	
	public static Hotel getInstance() {
		if (instance == null) {
			instance = new Hotel();
		}
		return instance;
	}
	
	public void setParameters(String[] paths, GregorianCalendar now) {
		facade.setParameters(paths, now);
	}
	
	public void load() {
		facade.load();
	}
	
	public void save() {
		facade.save();
	}
	
	public ArrayList<String> sortRoomsByCapacity(){
		ArrayList<String> result = new ArrayList<>();
		for (Room room : facade.sortRoomsByCapacity(facade.getRooms())) {
			result.add(room.toString());
		}
		return result;
	}
	
	public ArrayList<String> getRooms() {
		ArrayList<String> result = new ArrayList<>();
		for (Room room : facade.getRooms()) {
			result.add(room.toString());
		}
		return result;
	}
	
}
