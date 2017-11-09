package com.senla.hotel.workers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.senla.hotel.comparators.room.RoomCapacityComparator;
import com.senla.hotel.comparators.room.RoomPriceComparator;
import com.senla.hotel.comparators.room.RoomStarComparator;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.RoomStatus;
import com.senla.hotel.repositories.RoomRepository;

public class RoomWorker {
	private RoomRepository roomRepository;

	public RoomWorker() {
		roomRepository = new RoomRepository();
	}

	public Boolean add(Room room) {
		return roomRepository.add(room);
	}

	public void load(String[] roomData) {
		for (String data : roomData) {
			if (data.compareTo("")!=0) {
				add(new Room(data));
			}
		}
	}

	public Room getRoomByID(Integer roomID) {
		return roomRepository.getByID(roomID);
	}

	private Room[] sortByPrice(Room[] rooms) {
		Arrays.sort(rooms, new RoomPriceComparator());
		return rooms;
	}

	private Room[] sortByCapacity(Room[] rooms) {
		Arrays.sort(rooms, new RoomCapacityComparator());
		return rooms;
	}

	private Room[] sortByStar(Room[] rooms) {
		Arrays.sort(rooms, new RoomStarComparator());
		return rooms;
	}

	public Room[] getFreeRooms() {
		List<Room> list = new ArrayList<>();
		for (Room room : roomRepository.getRooms()) {
			if (room.getStatus().equals(RoomStatus.FREE)) {
				list.add(room);
			}
		}
		return list.toArray(new Room[list.size()]);
	}

	public int getFreeRoomsCount() {
		return getFreeRooms().length;
	}

	public Room[] getSortedByCapacity(Boolean isFree) {
		if (isFree) {
			return sortByCapacity(getFreeRooms());
		} else {
			return sortByCapacity(getRooms());
		}
	}

	public Room[] getSortedByPrice(Boolean isFree) {
		if (isFree) {
			return sortByPrice(getFreeRooms());
		} else {
			return sortByPrice(getRooms());
		}
	}

	public Room[] getSortedByStar(Boolean isFree) {
		if (isFree) {
			return sortByStar(getFreeRooms());
		} else {
			return sortByStar(getRooms());
		}
	}

	public Room[] getRooms() {
		return roomRepository.getRooms().toArray(new Room[roomRepository.getCount()]);
	}

	public String[] makeWriteableArray() {
		List<String> result = new ArrayList<>();
		for (Room room : roomRepository.getRooms()) {
			result.add(room.toString());
		}
		return result.toArray(new String[result.size()]);
	}

}
