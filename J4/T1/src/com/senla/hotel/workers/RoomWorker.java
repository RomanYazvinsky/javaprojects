package com.senla.hotel.workers;

import java.util.Arrays;

import com.senla.hotel.comparators.room.RoomCapacityComparator;
import com.senla.hotel.comparators.room.RoomPriceComparator;
import com.senla.hotel.comparators.room.RoomStarComparator;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.RoomStatus;
import com.senla.hotel.repositories.RoomRepository;

import utilities.ArrayWorker;

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
		int entityCount = ArrayWorker.getCount(getRooms());
		int freeRoomsCount = 0;
		Room[] freeRooms = new Room[entityCount];
		for (int i = 0; i < entityCount; i++) {
			if (getRooms()[i].getStatus() == RoomStatus.FREE) {
				freeRooms[freeRoomsCount++] = getRooms()[i];
			}
		}
		return ArrayWorker.castToRoom(ArrayWorker.decreaseArray(freeRooms));
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
		return roomRepository.getRooms();
	}

	public String[] makeWriteableArray() {
		int count = roomRepository.getCount();
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = roomRepository.getRooms()[i].toString();
		}
		return result;
	}

}
