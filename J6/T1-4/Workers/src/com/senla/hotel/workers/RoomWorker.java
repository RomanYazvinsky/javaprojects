package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.senla.hotel.entities.Room;
import com.senla.hotel.repositories.RoomRepository;

public class RoomWorker {
	private RoomRepository roomRepository;

	public RoomWorker() {
		roomRepository = RoomRepository.getInstance();
	}

	public Boolean add(Room room) {
		return roomRepository.add(room);
	}

	public Boolean addNoIDGenerating(Room room) {
		return roomRepository.addNoIDGenerating(room);
	}
	
	public void load(String path) throws ClassNotFoundException, IOException {
		roomRepository.load(path);
	}

	public void save(String path) throws IOException {
		roomRepository.save(path);
	}

	public ArrayList<Room> getRooms() {
		return roomRepository.getRooms();
	}

	public Room getRoomByID(Integer roomID) {
		if (roomID == null) {
			return null;
		}
		return roomRepository.getByID(roomID);
	}

	public ArrayList<Room> sort(ArrayList<Room> rooms, Comparator<Room> comparator) {
		Collections.sort(rooms, comparator);
		return rooms;
	}

	public String[] toStringArray(ArrayList<Room> rooms) {
		List<String> result = new ArrayList<>();
		for (Room room : rooms) {
			result.add(room.toString());
		}
		return result.toArray(new String[result.size()]);
	}

	public void export(Room room) {
		roomRepository.export(room);
	}
	
	public Boolean delete (Room room) {
		return roomRepository.delete(room);
	}
}
