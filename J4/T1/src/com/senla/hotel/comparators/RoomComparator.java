package com.senla.hotel.comparators;

import com.senla.hotel.comparators.room.RoomCapacityComparator;
import com.senla.hotel.comparators.room.RoomPriceComparator;
import com.senla.hotel.comparators.room.RoomStarComparator;

public class RoomComparator {
	public RoomPriceComparator priceComparator;
	public RoomCapacityComparator capacityComparator;
	public RoomStarComparator starComparator;

	public RoomComparator() {
		this.priceComparator = new RoomPriceComparator();
		this.capacityComparator = new RoomCapacityComparator();
		this.starComparator = new RoomStarComparator();
	}

}
