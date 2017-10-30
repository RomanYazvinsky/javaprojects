import java.util.Date;

import by.hotel.entities.Booking;
import by.hotel.entities.Client;
import by.hotel.entities.Room;
import by.hotel.managers.BookingManager;
import by.hotel.managers.ClientManager;
import by.hotel.managers.RoomManager;

public class Manager {
	BookingManager bookingManager;
	ClientManager clientManager;
	RoomManager roomManager;

	public Manager() {
		bookingManager = new BookingManager();
		clientManager = new ClientManager();
		roomManager = new RoomManager();
	}
	
	public Boolean addBooking(Booking booking) {
		return bookingManager.addBooking(booking);
	}
	
	public Boolean addClient(Client client) {
		return clientManager.addClient(client);
	}
	
	public Boolean addRoom(Room room) {
		return roomManager.addRoom(room);
	}
	
	public void synchronize(Date now) {
		bookingManager.updateClientBookings(now);
		bookingManager.updateRoomClients(now);
	}
	
	public String[] getSortedRooms(int sortType, Boolean isFree) {
		return roomManager.getListOfRooms(sortType, isFree);
	}
}
