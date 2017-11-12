package runner;

import com.senla.hotel.ui.MenuController;

public class Main {
	public static void main(String[] args) {
		MenuController menu = new MenuController();
		menu.run();
	/*	Facade manager = Filler.fill(args, now);
		manager.load();

		manager.getActualOrders(now);

		
		Filler.rm1.setStatus(RoomStatus.ONSERVICE);
		manager.getRooms();
		jump();
		manager.getFreeRoomsCount();
		manager.getActualClientCount(now);
		manager.getActualOrders(now);
		manager.getOrders();
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
		manager.getServicesOfClient(manager.getClientByID(Filler.or1.getClientID()));
		manager.save();*/
	}

	public static void jump() {
		System.out.println();
	}
}
