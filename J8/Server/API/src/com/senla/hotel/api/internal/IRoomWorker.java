package com.senla.hotel.api.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.EmptyObjectException;

public interface IRoomWorker {

	Boolean add(Room room, boolean addId);

	void load(String path) throws ClassNotFoundException, IOException, EmptyObjectException;

	void save(String path) throws IOException;

	ArrayList<Room> getRooms();

	Room getRoomByID(Integer roomID);

	ArrayList<Room> sort(ArrayList<Room> rooms, Comparator<Room> comparator);

	String[] toStringArray(ArrayList<Room> rooms);

	ArrayList<Room> importAll() throws EmptyObjectException;

	void exportAll();

	Boolean delete(Room room);

}