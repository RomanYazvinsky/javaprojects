package com.senla.hotel.repositories;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Room;

import utilities.IDGenerator;

public class RoomRepository implements IEntityRepository {
	private HashSet<Room> rooms;
	private static RoomRepository instance;

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
			if (room.getID().equals(id)) {
				return room;
			}
		}
		return null;

	}

	@Override
	public void save(String path) throws IOException {
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;
		fileOutputStream = new FileOutputStream(path);
		objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(rooms);
		objectOutputStream.flush();
		objectOutputStream.close();
	}

	@Override
	public void load(String path) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream;
		ObjectInputStream objectInputStream;
		fileInputStream = new FileInputStream(path);
		objectInputStream = new ObjectInputStream(fileInputStream);
		rooms = (HashSet<Room>) objectInputStream.readObject();
		objectInputStream.close();
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
		entity.setID(IDGenerator.createRoomID());
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
