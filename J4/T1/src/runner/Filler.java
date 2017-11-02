package runner;

import java.util.GregorianCalendar;

import com.senla.hotel.Facade;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.enums.RoomStatus;

public class Filler {
	public static Client cl1 = new Client("roma");
	public static Client cl2 = new Client("vasya");
	public static Client cl3 = new Client("igor");
	public static Client cl4 = new Client("Benedict");
	public static Client cl5 = new Client("wow");
	public static Client cl6 = new Client("ahhahaha");
	public static Client cl7 = new Client("ha");
	public static Client cl8 = new Client("as");

	public static Service sr1 = new Service(10, "bar", new GregorianCalendar(2017, 11, 02, 0, 0, 0));
	public static Service sr2 = new Service(13, "notBar", new GregorianCalendar(2017, 11, 02, 0, 0, 0));
	public static Service sr3 = new Service(6, "anotherBar", new GregorianCalendar(2017, 11, 02, 0, 0, 0));

	public static Service[] sra1 = new Service[] { sr1, sr2, sr3 };
	public static Service[] sra2 = new Service[] { sr3 };
	public static Service[] sra3 = new Service[] { sr1, sr3 };

	public static Room rm1 = new Room(2, 2, RoomStatus.FREE, 10);
	public static Room rm2 = new Room(3, 2, RoomStatus.FREE, 10);
	public static Room rm3 = new Room(1, 5, RoomStatus.FREE, 10);
	public static Room rm4 = new Room(3, 2, RoomStatus.FREE, 10);
	public static Room rm5 = new Room(1, 1, RoomStatus.FREE, 10);
	public static Room rm6 = new Room(5, 3, RoomStatus.FREE, 10);
	public static Room rm7 = new Room(1, 2, RoomStatus.FREE, 10);
	public static Room rm8 = new Room(4, 3, RoomStatus.FREE, 10);
	public static Room rm9 = new Room(1, 4, RoomStatus.FREE, 10);

	public static Order or1;
	public static Order or2 ;
	public static Order or3 ;
	public static Order or4;
	public static Order or5;
	public static Order or6;
	public static Order or7;
	
	public static Facade fill(String[] args, GregorianCalendar now) {
		Facade manager = new Facade(args, now);
		manager.addClient(cl1);
		manager.addClient(cl2);
		manager.addClient(cl3);
		manager.addClient(cl4);
		manager.addClient(cl5);
		manager.addClient(cl6);
		manager.addClient(cl7);
		manager.addClient(cl8);

		manager.addRoom(rm1);
		manager.addRoom(rm2);
		manager.addRoom(rm3);
		manager.addRoom(rm4);
		manager.addRoom(rm5);
		manager.addRoom(rm6);
		manager.addRoom(rm7);
		manager.addRoom(rm8);
		manager.addRoom(rm9);
		
		or1 = new Order(rm1.getID(), cl1.getID(), new GregorianCalendar(2017, 11, 1, 0, 0, 0), new GregorianCalendar(2017, 11, 11, 0, 0, 0), sra1);
		or2 = new Order(rm1.getID(), cl2.getID(), new GregorianCalendar(2017, 11, 1, 0, 0, 0), new GregorianCalendar(2017, 11, 10, 0, 0, 0), sra1);
		or3 = new Order(rm1.getID(), cl3.getID(), new GregorianCalendar(2017, 11, 1, 0, 0, 0), new GregorianCalendar(2017, 11, 15, 0, 0, 0), null);
		or4 = new Order(rm1.getID(), cl4.getID(), new GregorianCalendar(2017, 11, 1, 0, 0, 0), new GregorianCalendar(2017, 12, 1, 0, 0, 0), sra2);
		or5 = new Order(rm2.getID(), cl3.getID(), new GregorianCalendar(2017, 9, 1, 0, 0, 0), new GregorianCalendar(2017, 10, 5, 0, 0, 0), sra2);
		or6 = new Order(rm4.getID(), cl5.getID(), new GregorianCalendar(2017, 10, 1, 0, 0, 0), new GregorianCalendar(2017, 11, 3, 0, 0, 0), sra3);
		or7 = new Order(rm4.getID(), cl6.getID(), new GregorianCalendar(2017, 11, 1, 0, 0, 0), new GregorianCalendar(2017, 11, 1, 0, 0, 0), sra3);
		
		
		manager.addOrder(or1);
		manager.addOrder(or2);
		manager.addOrder(or3);
		manager.addOrder(or4);
		manager.addOrder(or5);
		manager.addOrder(or6);
		manager.addOrder(or7);

		manager.closeOrder(or5);
		
		manager.addService(sr1);
		manager.addService(sr2);
		manager.addService(sr3);
		
		return manager;
	}
	
}
