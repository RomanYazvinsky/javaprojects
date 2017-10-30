package by.hotel.comparators;

public class RoomComparators {
	public RoomPriceComparator priceComparator;
	public RoomCapacityComparator capacityComparator;
	public RoomStarComparator starComparator;

	public RoomComparators() {
		this.priceComparator = new RoomPriceComparator();
		this.capacityComparator = new RoomCapacityComparator();
		this.starComparator = new RoomStarComparator();
	}

}
