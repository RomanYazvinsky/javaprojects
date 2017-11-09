package com.senla.hotel.comparators.room;

import java.util.Comparator;

import com.senla.hotel.entities.Room;

public class RoomStarComparator implements Comparator<Room> {

	@Override
	public int compare(Room o1, Room o2) {
		if (o1 != null && o2 != null) {
			return o1.getStar().compareTo(o2.getStar());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
