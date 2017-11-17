package com.senla.hotel.workers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.senla.hotel.entities.Room;
import com.senla.hotel.enums.RoomStatus;
import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.repositories.RoomRepository;

public class RoomWorker {
	private RoomRepository roomRepository;

	public RoomWorker() {
		roomRepository = RoomRepository.getInstance();
	}

	public Boolean add(Room room) {
		return roomRepository.add(room);
	}

	public void load(String[] roomData) throws Exception {
		try {
			for (String data : roomData) {
				if (data.compareTo("") != 0) {
					add(new Room(data));
				}
			}
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectParameterException e) {
			throw e;
		}
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

	public ArrayList<Room> getFreeRooms() {
		ArrayList<Room> rooms = new ArrayList<>();
		for (Room room : roomRepository.getRooms()) {
			if (room.getStatus().equals(RoomStatus.FREE)) {
				rooms.add(room);
			}
		}
		return rooms;
	}

	public int getFreeRoomsCount() {
		return getFreeRooms().size();
	}

	public ArrayList<Room> getRooms() {
		return roomRepository.getRooms();
	}

	public String[] toStringArray(ArrayList<Room> rooms) {
		List<String> result = new ArrayList<>();
		for (Room room : rooms) {
			result.add(room.toString());
		}
		return result.toArray(new String[result.size()]);
	}

}
