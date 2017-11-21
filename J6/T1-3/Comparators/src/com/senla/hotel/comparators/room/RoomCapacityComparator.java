package com.senla.hotel.comparators.room;

import java.util.Comparator;

import com.senla.hotel.entities.Room;

public class RoomCapacityComparator implements Comparator<Room> {

	@Override
	public int compare(Room o1, Room o2) {
		if (o1 != null && o2 != null) {
			return o1.getCapacity().compareTo(o2.getCapacity());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
