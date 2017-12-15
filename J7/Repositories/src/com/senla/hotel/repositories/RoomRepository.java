package com.senla.hotel.repositories;

import com.senla.hotel.entities.Room;

import utilities.IDGenerator;

public class RoomRepository extends AEntityRepository<Room> {
	private static RoomRepository instance;


	private RoomRepository() {
	}

	public static RoomRepository getInstance() {
		if (instance == null) {
			instance = new RoomRepository();
		}
		return instance;
	}

	public Boolean add(Room entity, boolean addId) {
		if (addId) {
			entity.setId(IDGenerator.createRoomID());
		}
		Boolean result = entities.add(entity);
		if (result) {
			IDGenerator.addClientID(entity.getId());
		}
		return result;
	}
	
}
