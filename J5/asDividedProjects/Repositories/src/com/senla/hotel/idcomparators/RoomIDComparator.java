package com.senla.hotel.idcomparators;

import java.util.Comparator;

import com.senla.hotel.entities.Room;

public class RoomIDComparator implements Comparator<Room> {

	@Override
	public int compare(Room o1, Room o2) {
		if (o1 != null && o2 != null) {
			return o1.getID().compareTo(o2.getID());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
