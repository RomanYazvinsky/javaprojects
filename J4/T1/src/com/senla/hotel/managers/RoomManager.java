package com.senla.hotel.managers;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Room;
import com.senla.hotel.utilities.ArrayWorker;

public class RoomManager extends AEntityManager {
	private static Room[] rooms;

	public RoomManager() {
		super();
		if (RoomManager.rooms == null) {
			entities = new Room[6];
			RoomManager.rooms = ArrayWorker.castToRoom(entities);
		}else {
			entities = RoomManager.rooms;
		}
	}

	public Room getRoomByID(Integer id) {
		if (getByID(id) == null)
			return null;
		return (Room) getByID(id);

	}

	public Boolean deleteClient(Integer roomID, Integer clientID) {
		return getRoomByID(roomID).deleteClient(clientID);
	}

	@Override
	public int find(AEntity entity) {
		for (int i = 0; i < ArrayWorker.getCount(rooms); i++) {
			if (rooms[i].equals(entity)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	protected Boolean isCorrectType(AEntity entity) {
		return entity instanceof Room;
	}

	@Override
	protected void extendArray() {
		RoomManager.rooms = (Room[]) ArrayWorker.extendEntityArray(RoomManager.rooms);
		entities = RoomManager.rooms;
	}

	public Room[] getRooms() {
		return RoomManager.rooms;
	}

	@Override
	public int getCount() {
		return ArrayWorker.getCount(RoomManager.rooms);
	}

}
