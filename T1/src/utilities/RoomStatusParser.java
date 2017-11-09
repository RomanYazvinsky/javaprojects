package utilities;

import com.senla.hotel.entities.RoomStatus;

public class RoomStatusParser {
	public static RoomStatus define(String state) {
		if (state.compareTo("FREE") == 0) {
			return RoomStatus.FREE;
		}
		if (state.compareTo("ONSERVICE") == 0) {
			return RoomStatus.ONSERVICE;
		}
		if (state.compareTo("PARTIALLY_FREE") == 0) {
			return RoomStatus.PARTIALLY_FREE;
		}
		if (state.compareTo("USED") == 0) {
			return RoomStatus.USED;
		}
		return null;
	}
}
