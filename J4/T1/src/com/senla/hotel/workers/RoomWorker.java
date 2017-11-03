package com.senla.hotel.workers;

import java.util.Arrays;

import com.senla.hotel.comparators.RoomComparator;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.RoomStatus;
import com.senla.hotel.managers.RoomManager;
import com.senla.hotel.utilities.ArrayWorker;

public class RoomWorker {
	private RoomManager roomManager;
	private RoomComparator roomComparator;

	public RoomWorker() {
		roomManager = new RoomManager();
		roomComparator = new RoomComparator();
	}

	public Boolean add(Room room) {
		return roomManager.add(room);
	}

	public void load(String[] roomData) {
		for (String data : roomData) {
			if (data.compareTo("")!=0) {
				add(new Room(data));
			}
		}
	}

	public Room getRoomByID(Integer roomID) {
		return roomManager.getRoomByID(roomID);
	}

	private Room[] sortByPrice(Room[] rooms) {
		Arrays.sort(rooms, roomComparator.priceComparator);
		return rooms;
	}

	private Room[] sortByCapacity(Room[] rooms) {
		Arrays.sort(rooms, roomComparator.capacityComparator);
		return rooms;
	}

	private Room[] sortByStar(Room[] rooms) {
		Arrays.sort(rooms, roomComparator.starComparator);
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
		return roomManager.getRooms();
	}

	public String[] makeWriteableArray() {
		int count = roomManager.getCount();
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = roomManager.getRooms()[i].toString();
		}
		return result;
	}

}
