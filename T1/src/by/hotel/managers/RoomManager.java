package by.hotel.managers;

import java.util.Arrays;

import by.hotel.comparators.RoomComparators;
import by.hotel.entities.AEntity;
import by.hotel.entities.Room;
import by.hotel.entities.RoomStatus;

public class RoomManager extends AEntityManager {
	private RoomComparators comparators;
	Room[] roomArray;
	public RoomManager() {
		super();
		roomArray = (Room[]) (entityArray = new Room[6]);
		comparators = new RoomComparators();
	}

	public Boolean addRoom(Room room) {
		return addEntity(room);
	}

	private String[] doNotSort(Room[] roomArray) {
		return makeStringArray(roomArray);
	}

	private Room[] decreaseRoomArray(Room[] rooms, int count) {
		if (count == rooms.length) {
			return rooms;
		}
		Room[] newRooms = new Room[count];
		System.arraycopy(rooms, 0, newRooms, 0, count);
		return newRooms;
	}

	private String[] makeStringArray(Room[] roomArray) {
		String[] output = new String[entityArray.length];
		StringBuilder roomData = new StringBuilder();
		for (int i = 0; i < entityArray.length; i++) {
			roomData.append(roomArray[i].getNumber()).append(" ")
					.append(roomArray[i].getPricePerDay()).append(" ")
					.append(roomArray[i].getCapacity()).append(" ")
					.append(roomArray[i].getStar());
			output[i] = roomData.toString();
			roomData.delete(0, roomData.length());
		}
		return output;
	}

	private String[] sortByPrice(Room[] roomArray) {
		Arrays.sort(roomArray, comparators.priceComparator);
		return makeStringArray(roomArray);
	}

	private String[] sortByCapacity(Room[] roomArray) {
		Arrays.sort(roomArray, comparators.capacityComparator);
		return makeStringArray(roomArray);
	}

	private String[] sortByStar(Room[] roomArray) {
		Arrays.sort(roomArray, comparators.starComparator);
		return makeStringArray(roomArray);
	}

	@Override
	protected int findEntity(AEntity entity) {
		for (int i = 0; i < entityCount; i++) {
			if (((Room) entityArray[i]).equals((Room) entity)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	protected Boolean isCorrectType(AEntity entity) {
		return entity instanceof Room;
	}

	public int getNumberOfFreeRooms() {
		return getListOfRooms(0, true).length;
	}

	public String[] getListOfRooms(int sortType, Boolean isFree) {
		if (isFree) {
			switch (sortType) {
				case 0: {
					return doNotSort(getFreeRooms());
				}
				case 1: {
					return sortByPrice(getFreeRooms());
				}
				case 2: {
					return sortByCapacity(getFreeRooms());
				}
				case 3: {
					return sortByStar(getFreeRooms());
				}
				default:{
					break;
				}
			}
		} else {
			switch (sortType) {
				case 0: {
					return doNotSort(decreaseRoomArray(roomArray, entityCount));
				}
				case 1: {
					return sortByPrice(decreaseRoomArray(roomArray, entityCount));
				}
				case 2: {
					return sortByCapacity(decreaseRoomArray(roomArray, entityCount));
				}
				case 3: {
					return sortByStar(decreaseRoomArray(roomArray, entityCount));
				}
				default:{
					break;
				}
			}
		}
		return null;
	}

	public Room[] getFreeRooms() {
		int freeRoomsCount = 0;
		Room[] freeRooms = new Room[entityCount];
		for (int i = 0; i < entityCount; i++) {
			if (roomArray[i].getStatus() == RoomStatus.FREE) {
				freeRooms[freeRoomsCount++] = roomArray[i];
			}
		}
		return decreaseRoomArray(freeRooms, freeRoomsCount);
	}

}
