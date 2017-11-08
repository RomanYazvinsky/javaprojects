package com.senla.hotel.repositories;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Room;

import utilities.ArrayWorker;
import utilities.IDGenerator;

public class RoomRepository implements IEntityRepository {
	private static Room[] rooms;

	public RoomRepository() {
		rooms = new Room[6];
	}

	@Override
	public Room getByID(Integer id) {
		for (Room room : rooms) {
			if (room != null) {
				if (room.getID().equals(id)) {
					return room;
				}
			}
		}
		return null;

	}

	public Boolean deleteClient(Integer roomID, Integer clientID) {
		return getByID(roomID).deleteClient(clientID);
	}

	@Override
	public int getIndex(AEntity entity) {
		for (int i = 0; i < ArrayWorker.getCount(rooms); i++) {
			if (rooms[i].equals(entity)) {
				return i;
			}
		}
		return -1;
	}

	public Room[] getRooms() {
		return rooms;
	}

	@Override
	public int getCount() {
		return ArrayWorker.getCount(rooms);
	}

	@Override
	public Boolean add(AEntity entity) {
		int orderCount = ArrayWorker.getCount(rooms);
		if (getIndex(entity) >= 0 && orderCount > 0) {
			return false;
		}
		if (orderCount == rooms.length) {
			rooms = (Room[]) ArrayWorker.extendEntityArray(rooms);
		}
		rooms[orderCount] = (Room) entity;
		IDGenerator.addRoomID(entity.getID());
		return true;
	}

}
