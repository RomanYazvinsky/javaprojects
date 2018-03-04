package com.senla.hotel.api.internal;

import java.util.ArrayList;

import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.EmptyObjectException;

public interface IRoomRepository {
	ArrayList<Room> get();

	Boolean add(Room entity, boolean addId);

	Boolean delete(Room entity);

	Room getByID(Integer id);

	int getCount();

	void set(ArrayList<Room> entities) throws EmptyObjectException;
}