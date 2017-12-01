package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.EmptyObjectException;

import utilities.CSVWorker;
import utilities.IDGenerator;

public class RoomRepository implements IEntityRepository {
	private static Logger logger;
	private HashSet<Room> rooms;
	private static RoomRepository instance;
	
	
	static {
		logger = Logger.getLogger(RoomRepository.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	private RoomRepository() {
		rooms = new HashSet<Room>();
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
			if (room.getId().equals(id)) {
				return room;
			}
		}
		return null;

	}

	public void setRooms(ArrayList<Room> rooms) throws EmptyObjectException {
		if (rooms == null) {
			logger.log(Level.SEVERE, "setRooms");
			throw new EmptyObjectException();
		}
		this.rooms = new HashSet<>(rooms);
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
		entity.setId(IDGenerator.createRoomID());
		Boolean result = rooms.add((Room) entity);
		if (result) {
			IDGenerator.addRoomID(entity.getId());
		}
		return result;
	}

	public Boolean addNoIDGenerating(Room room) {
		Boolean result = rooms.add(room);
		if (result) {
			IDGenerator.addClientID(room.getId());
		}
		return result;
	}

	@Override
	public Boolean delete(AEntity entity) {
		ArrayList<Room> list = getRooms();
		for (int i = 0; i < list.size(); i++) {
			if (entity.getId().equals(list.get(i).getId())) {
				return rooms.remove(list.get(i));
			}
		}
		return false;
	}

	public void export(Room room) {
		CSVWorker.exportRoom(room);
	}

}
