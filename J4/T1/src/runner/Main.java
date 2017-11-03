package runner;

import java.util.GregorianCalendar;

import com.senla.hotel.Facade;
import com.senla.hotel.entities.RoomStatus;
import com.senla.hotel.sorttypes.OrderSortType;
import com.senla.hotel.sorttypes.RoomSortType;
import com.senla.hotel.sorttypes.ServiceSortType;
import com.senla.hotel.utilities.DateCreator;

public class Main {
	public static void main(String[] args) {
		GregorianCalendar now = DateCreator.parseString("02 11 2017");
		Facade manager = Filler.fill(args, now);
		manager.load();

		Filler.rm1.setStatus(RoomStatus.ONSERVICE);
		manager.getRooms(RoomSortType.NO, true);
		jump();
		manager.getFreeRoomsCount();
		manager.getActualClientCount();
		manager.getActualOrder();
		manager.getOrders(OrderSortType.DATE);
		jump();
		manager.getFreeRoomByDate(new GregorianCalendar(2017, 11, 12, 0, 0, 0));

		manager.getPriceForRoom(Filler.cl5);
		manager.getPriceForServices(manager.getActualOrder(Filler.cl5));
		jump();
		manager.getLastOrdersOfRoom(Filler.rm1, 5);
		Filler.rm1.setPricePerDay(11);
		jump();
		manager.getServices();
		jump();
		manager.getServicesOfClient(manager.getClientByID(Filler.or1.getClientID()), ServiceSortType.NO);
		manager.save();
	}

	public static void jump() {
		System.out.println();
	}
}
