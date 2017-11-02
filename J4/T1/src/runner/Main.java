package runner;

import java.util.GregorianCalendar;

import com.senla.hotel.Facade;
import com.senla.hotel.entities.Order;
import com.senla.hotel.enums.OrderSortType;
import com.senla.hotel.enums.RoomSortType;
import com.senla.hotel.enums.RoomStatus;

public class Main {
	public static void main(String[] args) {
		GregorianCalendar now = new GregorianCalendar(2017, 11, 2, 0, 0, 0);
		Facade manager = Filler.fill(args, now);
		manager.load();
		
		Filler.rm1.setStatus(RoomStatus.ONSERVICE);
		Printer.printEntityArray(manager.getRooms(RoomSortType.NO, true));
		jump();
		System.out.println(manager.getFreeRoomsCount());
		System.out.println(manager.getActualClientCount());
		Printer.printOrderArray(manager.getActualOrder());
		Order[] orders = manager.getOrders(OrderSortType.DATE);
		for (int i = 0; i < orders.length; i++) {
			System.out.print(manager.getClientByID(orders[i].getClientID()).toString() + " || "
					+ manager.getRoomByID(orders[i].getRoomID()).toString() + " || ");
			Printer.printDate(orders[i].getOrderTo());
			jump();
		}
		jump();
		Printer.printEntityArray(manager.getFreeRoomByDate(new GregorianCalendar(2017, 11, 12, 0, 0, 0)));
		System.out.println(
				manager.getPriceForRoom(Filler.cl5) + manager.getPriceForServices(manager.getActualOrder(Filler.cl5)));
		jump();
		Printer.printOrderArray(manager.getLastOrdersOfRoom(Filler.rm1, 5));
		Filler.rm1.setPricePerDay(11);
		jump();
		Printer.printEntityArray(manager.getServices());
		jump();
		Printer.printEntityArray(Filler.or4.getServices());
		manager.save();
	}

	public static void jump() {
		System.out.println();
	}
}
