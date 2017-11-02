package com.senla.hotel;

import java.util.GregorianCalendar;

import com.danco.training.TextFileWorker;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.enums.OrderSortType;
import com.senla.hotel.enums.RoomSortType;
import com.senla.hotel.enums.ServiceSortType;
import com.senla.hotel.utilities.Saver;
import com.senla.hotel.workers.ClientWorker;
import com.senla.hotel.workers.OrderWorker;
import com.senla.hotel.workers.RoomWorker;
import com.senla.hotel.workers.ServiceWorker;

public class Facade {
	private RoomWorker roomWorker;
	private ClientWorker clientWorker;
	private ServiceWorker serviceWorker;
	private OrderWorker orderWorker;
	private GregorianCalendar now;
	private String[] paths;

	public Facade(String[] paths, GregorianCalendar now) {
		this.now = now;
		roomWorker = new RoomWorker();
		clientWorker = new ClientWorker();
		serviceWorker = new ServiceWorker();
		orderWorker = new OrderWorker();
		this.paths = paths;
	}

	public void setDate(GregorianCalendar now) {
		this.now = now;
	}

	public Boolean addClient(Client client) {
		return clientWorker.add(client);
	}

	public Boolean addOrder(Order order) {
		return orderWorker.add(order);
	}

	public Boolean addRoom(Room room) {
		return roomWorker.add(room);
	}

	public Boolean addService(Service service) {
		return serviceWorker.add(service);
	}
	
	public Room getRoomByID(Integer roomID) {
		return roomWorker.getRoomByID(roomID);
	}
	
	public Client getClientByID(Integer clientID) {
		return clientWorker.getClientByID(clientID);
	}
	
	public Order getOrderByID(Integer orderID) {
		return orderWorker.getOrderByID(orderID);
	}
	
	public Service getServiceByID(Integer serviceID) {
		return serviceWorker.getServiceByID(serviceID);
	}

	public Room[] getRooms(RoomSortType sortType, Boolean isFree) {
		switch (sortType) {
		case NO: {
			if (isFree) {
				return roomWorker.getFreeRooms();
			} else {
				return roomWorker.getRooms();
			}
		}
		case CAPACITY: {
			return roomWorker.getSortedByCapacity(isFree);
		}
		case PRICE: {
			return roomWorker.getSortedByPrice(isFree);
		}
		case STARS: {
			return roomWorker.getSortedByStar(isFree);
		}
		default: {
			break;
		}
		}
		return roomWorker.getRooms();
	}

	public Order[] getOrders(OrderSortType sortType) {
		switch (sortType) {
		case NO: {
			return orderWorker.getOrders();
		}
		case DATE: {
			return orderWorker.getOrderSortedByDate();
		}
		case NAME: {
			return orderWorker.getOrdersSortedByClientName();
		}
		default:
			break;
		}
		return orderWorker.getOrders();
	}

	public Client[] getClients() {
		return clientWorker.getClients();
	}

	public Integer getFreeRoomsCount() {
		return roomWorker.getFreeRoomsCount();
	}

	public Integer getActualClientCount() {
		return orderWorker.getActualClientCount(now);
	}

	public Room[] getFreeRoomByDate(GregorianCalendar date) {
		return orderWorker.getFreeRoomByDate(date);
	}

	public Integer getPriceForRoom(Client client) {
		return orderWorker.getPriceForRoom(client, now);
	}

	public Order[] getLastOrdersOfRoom(Room room, Integer clientCount) {

		return orderWorker.getLastClients(room, clientCount);
	}

	public Service[] getServicesOfClient(Client client, ServiceSortType sortType) {
		switch (sortType) {
		case NO: {
			return orderWorker.getServicesOfClient(client);
		}
		case DATE: {
			return orderWorker.getServicesSortedByDate(client);
		}
		case NAME: {
			return orderWorker.getServicesSortedByName(client);
		}
		case PRICE: {
			return orderWorker.getServicesSortedByPrice(client);
		}
		default:
			break;
		}
		return orderWorker.getServicesOfClient(client);
	}

	public Order[] getActualOrder() {
		return orderWorker.getActualOrders(now);
	}
	
	public Service[] getServices() {
		return serviceWorker.getServices();
	}
	
	public Boolean closeOrder(Order order) {
		return orderWorker.closeOrder(order, now);
	}
	
	public Integer getPriceForServices(Order order) {
		return orderWorker.getPriceForServices(order);
	}
	
	public Order getActualOrder(Client client) {
		return orderWorker.getActualOrder(client, now);
	}
	
	public void loadServices() {
		serviceWorker.load(new TextFileWorker(paths[3]).readFromFile());
	}

	public void loadRooms() {
		roomWorker.load(new TextFileWorker(paths[2]).readFromFile());
	}

	public void loadClients() {
		clientWorker.load(new TextFileWorker(paths[1]).readFromFile());
	}

	public void loadOrders() {
		if (roomWorker.getRooms().length != 0 && clientWorker.getClients().length != 0) {
			orderWorker.load(new TextFileWorker(paths[0]).readFromFile());
		}
	}

	public void load() {
		loadServices();
		loadRooms();
		loadClients();
		loadOrders();
	}

	public void saveOrders() {
		Saver.save(paths[0], orderWorker.makeWriteableArray());
	}

	public void saveClients() {
		Saver.save(paths[1], clientWorker.makeWritableArray());
	}

	public void saveRooms() {
		Saver.save(paths[2], roomWorker.makeWriteableArray());
	}

	public void saveServices() {
		Saver.save(paths[3],serviceWorker.makeWriteableArray());
	}

	public void reset() {
		Saver.save(paths[0], new String[] {""});
		Saver.save(paths[1], new String[] {""});
		Saver.save(paths[2], new String[] {""});
		Saver.save(paths[3], new String[] {""});

	}
	
	public void save() {
		saveOrders();
		saveClients();
		saveRooms();
		saveServices();
	}
}
