package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.TreeSet;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Room;

import utilities.IDGenerator;

public class RoomRepository implements IEntityRepository {
	private TreeSet<Room> rooms;
	private static RoomRepository instance;

	private RoomRepository() {
		rooms = new TreeSet<Room>();
	}
	
	public static RoomRepository getInstance() {
		if (instance == null) {
			instance = new RoomRepository();
		}
		return instance;
	}

	@Override
	public Room getByID(Integer id) {
		for (Room room : rooms) {
			if (room.getID().equals(id)) {
				return room;
			}
		}
		return null;

	}

	public Boolean deleteClient(Integer roomID, Integer clientID) {
		return getByID(roomID).deleteClient(clientID);
	}

	public ArrayList<Room> getRooms() {
		return new ArrayList<Room>(rooms);
	}

	@Override
	public int getCount() {
		return rooms.size();
	}

	@Override
	public Boolean add(AEntity entity) {
		Boolean result = rooms.add((Room) entity);
		if (result) {
			IDGenerator.addRoomID(entity.getID());
		}
		return result;
	}

	@Override
	public Boolean delete(AEntity entity) {
		return rooms.remove(entity);
	}

}
