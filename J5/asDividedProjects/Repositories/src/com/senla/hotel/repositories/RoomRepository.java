package com.senla.hotel.repositories;

import java.util.TreeSet;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Room;
import com.senla.hotel.idcomparators.RoomIDComparator;

import utilities.IDGenerator;

public class RoomRepository implements IEntityRepository {
	private static TreeSet<Room> rooms;

	public RoomRepository() {
		rooms = new TreeSet<Room>(new RoomIDComparator());
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

	public TreeSet<Room> getRooms() {
		return rooms;
	}
	
	public void setRooms(TreeSet<Room> rooms) {
		RoomRepository.rooms = rooms;
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

}
