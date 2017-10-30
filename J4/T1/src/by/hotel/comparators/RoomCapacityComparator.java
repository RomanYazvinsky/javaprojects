package by.hotel.comparators;

import java.util.Comparator;

import by.hotel.entities.AEntity;
import by.hotel.entities.Room;

public class RoomCapacityComparator implements Comparator<Room> {

	@Override
	public int compare(Room o1, Room o2) {
		return o1.getCapacity() -  o2.getCapacity();
	}

}
