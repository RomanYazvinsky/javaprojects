package com.senla.hotel.facade;

import java.util.GregorianCalendar;

import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.sorttypes.OrderSortType;
import com.senla.hotel.sorttypes.RoomSortType;
import com.senla.hotel.sorttypes.ServiceSortType;
import com.senla.hotel.workers.ClientWorker;
import com.senla.hotel.workers.OrderWorker;
import com.senla.hotel.workers.RoomWorker;
import com.senla.hotel.workers.ServiceWorker;

import utilities.Loader;
import utilities.Saver;

public class Facade {
	private RoomWorker roomWorker;
	private ClientWorker clientWorker;
	private ServiceWorker serviceWorker;
	private OrderWorker orderWorker;
	private GregorianCalendar now;
	private String[] paths;

	public Facade(String[] paths, GregorianCalendar now) {
		if (paths.length != 4) {
			paths = new String[] { "orders.txt", "clients.txt", "rooms.txt", "services.txt" };
		}
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
		Boolean result = clientWorker.add(client);
		Printer.isSuccessful(result);
		return result;
	}

	public Boolean addOrder(Order order) {
		Boolean result = orderWorker.add(order);
		Printer.isSuccessful(result);
		return result;
	}

	public Boolean addRoom(Room room) {
		Boolean result = roomWorker.add(room);
		Printer.isSuccessful(result);
		return result;
	}

	public Boolean addService(Service service) {
		Boolean result = serviceWorker.add(service);
		Printer.isSuccessful(result);
		return result;
	}

	public Room getRoomByID(Integer roomID) {
		Room room = roomWorker.getRoomByID(roomID);
		Printer.printRoom(room);
		return room;
	}

	public Client getClientByID(Integer clientID) {
		Client client = clientWorker.getClientByID(clientID);
		Printer.printClient(client);
		return client;
	}

	public Order getOrderByID(Integer orderID) {
		Order order = orderWorker.getOrderByID(orderID);
		Printer.printOrder(order);
		return order;
	}

	public Service getServiceByID(Integer serviceID) {
		Service service = serviceWorker.getServiceByID(serviceID);
		Printer.printService(service);
		return service;
	}

	public Room[] getRooms(RoomSortType sortType, Boolean isFree) {
		Room[] rooms;
		switch (sortType) {
		case CAPACITY: {
			rooms = roomWorker.getSortedByCapacity(isFree);
			break;
		}
		case PRICE: {
			rooms = roomWorker.getSortedByPrice(isFree);
			break;
		}
		case STARS: {
			rooms = roomWorker.getSortedByStar(isFree);
			break;
		}
		default: {
			if (isFree) {
				rooms = roomWorker.getFreeRooms();
				break;
			} else {
				rooms = roomWorker.getRooms();
				break;
			}
		}
		}
		Printer.printEntityArray(rooms);
		return rooms;
	}

	public Order[] getOrders(OrderSortType sortType) {
		Order[] orders;
		switch (sortType) {
		case DATE: {
			orders = orderWorker.getOrderSortedByDate();
			break;
		}
		case NAME: {
			orders = orderWorker.getOrdersSortedByClientName();
			break;
		}
		default:
			orders = orderWorker.getOrders();
			break;
		}
		Printer.printOrderArray(orders);
		return orders;
	}

	public Client[] getClients() {
		Client[] clients = clientWorker.getClients();
		Printer.printEntityArray(clients);
		return clients;
	}

	public Integer getFreeRoomsCount() {
		Integer result = roomWorker.getFreeRoomsCount();
		Printer.print(result.toString());
		return result;
	}

	public Integer getActualClientCount() {
		Integer result = orderWorker.getActualClientCount(now);
		Printer.print(result.toString());
		return result;
	}

	public Room[] getFreeRoomByDate(GregorianCalendar date) {
		Room[] rooms = orderWorker.getFreeRoomByDate(date);
		Printer.printEntityArray(rooms);
		return rooms;
	}

	public Integer getPriceForRoom(Client client) {
		Integer result = orderWorker.getPriceForRoom(client, now);
		Printer.print(result.toString());
		return result;
	}

	public Order[] getLastOrdersOfRoom(Room room, Integer clientCount) {
		Order[] orders = orderWorker.getLastClients(room, clientCount);
		Printer.printOrderArray(orders);
		return orders;
	}

	public Service[] getServicesOfClient(Client client, ServiceSortType sortType) {
		Service[] services;
		switch (sortType) {
		case DATE: {
			services = orderWorker.getServicesSortedByDate(client);
			break;

		}
		case NAME: {
			services = orderWorker.getServicesSortedByName(client);
			break;

		}
		case PRICE: {
			services = orderWorker.getServicesSortedByPrice(client);
			break;

		}
		default:
			services = orderWorker.getServicesOfClient(client);
			break;
		}
		Printer.printServiceArray(services);
		return services;
	}

	public Order[] getActualOrder() {
		Order[] orders = orderWorker.getActualOrders(now);
		Printer.printOrderArray(orders);
		return orders;
	}

	public Service[] getServices() {
		Service[] services = serviceWorker.getServices();
		Printer.printServiceArray(services);
		return services;
	}

	public Boolean closeOrder(Order order) {
		Boolean result = orderWorker.closeOrder(order, now);
		Printer.isSuccessful(result);
		return result;
	}

	public Integer getPriceForServices(Order order) {
		Integer result = orderWorker.getPriceForServices(order);
		Printer.print(result.toString());
		return result;
	}

	public Order getActualOrder(Client client) {
		Order order = orderWorker.getActualOrder(client, now);
		Printer.printOrder(order);
		return order;
	}

	public void loadServices() {
		serviceWorker.load(Loader.load(paths[3]));
	}

	public void loadRooms() {
		roomWorker.load(Loader.load(paths[2]));
	}

	public void loadClients() {
		clientWorker.load(Loader.load(paths[1]));
	}

	public void loadOrders() {
		if (roomWorker.getRooms().length != 0 && clientWorker.getClients().length != 0) {
			orderWorker.load(Loader.load(paths[0]));
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
		Saver.save(paths[3], serviceWorker.makeWriteableArray());
	}

	public void reset() {
		Saver.save(paths[0], new String[] { "" });
		Saver.save(paths[1], new String[] { "" });
		Saver.save(paths[2], new String[] { "" });
		Saver.save(paths[3], new String[] { "" });

	}

	public void save() {
		saveOrders();
		saveClients();
		saveRooms();
		saveServices();
	}
}
