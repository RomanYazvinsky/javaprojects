package com.senla.hotel.facade;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.senla.hotel.comparators.order.*;
import com.senla.hotel.comparators.room.*;
import com.senla.hotel.comparators.service.*;
import com.senla.hotel.entities.*;
import com.senla.hotel.workers.*;

import utilities.Loader;
import utilities.Saver;

public class Facade {
	private RoomWorker roomWorker;
	private ClientWorker clientWorker;
	private ServiceWorker serviceWorker;
	private OrderWorker orderWorker;
	private GregorianCalendar now;
	private String[] paths;

	public Facade() {
		paths = new String[] { "orders.txt", "clients.txt", "rooms.txt", "services.txt" };
		now = new GregorianCalendar();
		roomWorker = new RoomWorker();
		clientWorker = new ClientWorker();
		serviceWorker = new ServiceWorker();
		orderWorker = new OrderWorker();
	}

	public void setParameters(String[] paths, GregorianCalendar now) {
		if (paths == null || paths.length != 4) {
			paths = new String[] { "orders.txt", "clients.txt", "rooms.txt", "services.txt" };
		}
		this.paths = paths;
		if (now == null) {
			now = new GregorianCalendar();
		}
		this.now = now;
	}

	public void setDate(GregorianCalendar now) {
		this.now = now;
	}

	public Boolean addClient(Client client) {
		Boolean result = clientWorker.add(client);
		return result;
	}

	public Boolean addOrder(Order order) {
		Boolean result = orderWorker.add(order);
		return result;
	}

	public Boolean addRoom(Room room) {
		Boolean result = roomWorker.add(room);
		return result;
	}

	public Boolean addService(Service service) {
		Boolean result = serviceWorker.add(service);
		return result;
	}

	public Room getRoomByID(Integer roomID) {
		Room room = roomWorker.getRoomByID(roomID);
		return room;
	}

	public Client getClientByID(Integer clientID) {
		Client client = clientWorker.getClientByID(clientID);
		return client;
	}

	public Order getOrderByID(Integer orderID) {
		Order order = orderWorker.getOrderByID(orderID);
		return order;
	}

	public Service getServiceByID(Integer serviceID) {
		Service service = serviceWorker.getServiceByID(serviceID);
		return service;
	}

	public ArrayList<Room> getRooms() {
		return roomWorker.getRooms();
	}

	public ArrayList<Room> getFreeRooms() {
		return roomWorker.getFreeRooms();
	}

	public ArrayList<Room> sortRoomsByCapacity(ArrayList<Room> rooms) {
		return roomWorker.sort(rooms, new RoomCapacityComparator());
	}

	public ArrayList<Room> sortRoomsByPrice(ArrayList<Room> rooms) {
		return roomWorker.sort(rooms, new RoomPriceComparator());
	}

	public ArrayList<Room> sortRoomsByStar(ArrayList<Room> rooms) {
		return roomWorker.sort(rooms, new RoomStarComparator());
	}

	public ArrayList<Order> getOrders() {
		return orderWorker.getOrders();
	}

	public ArrayList<Order> getActualOrders(GregorianCalendar date) {
		return orderWorker.getActualOrders(date);
	}

	public ArrayList<Order> sortOrdersByDate(ArrayList<Order> orders) {
		return orderWorker.sort(orders, new OrderDateComparator());
	}

	public ArrayList<Order> sortOrdersByName(ArrayList<Order> orders) {
		return orderWorker.sort(orders, new OrderClientNameComparator());
	}

	public ArrayList<Client> getClients() {
		return clientWorker.getClients();
	}

	public Integer getFreeRoomsCount() {
		Integer result = roomWorker.getFreeRoomsCount();
		return result;
	}

	public Integer getActualClientCount(GregorianCalendar date) {
		Integer result = orderWorker.getActualClientCount(date);
		return result;
	}

	public ArrayList<Room> getFreeRoomByDate(GregorianCalendar date) {
		ArrayList<Room> rooms = orderWorker.getFreeRoomByDate(date);
		return rooms;
	}

	public Integer getPriceForRoom(Client client) {
		Integer result = orderWorker.getPriceForRoom(client, now);
		return result;
	}

	public ArrayList<Order> getLastOrdersOfRoom(Room room, Integer clientCount) {
		ArrayList<Order> orders = orderWorker.getLastClients(room, clientCount);
		return orders;
	}

	public ArrayList<Service> getServicesOfClient(Client client) {
		return orderWorker.getServicesOfClient(client);
	}

	public ArrayList<Service> sortServicesByDate(ArrayList<Service> services) {
		return serviceWorker.sort(services, new ServiceDateComparator());
	}

	public ArrayList<Service> sortServicesByName(ArrayList<Service> services) {
		return serviceWorker.sort(services, new ServiceNameComparator());
	}

	public ArrayList<Service> sortServicesByPrice(ArrayList<Service> services) {
		return serviceWorker.sort(services, new ServicePriceComparator());
	}

	public ArrayList<Service> getServices() {
		ArrayList<Service> services = serviceWorker.getServices();
		return services;
	}

	public Boolean closeOrder(Order order) {
		Boolean result = orderWorker.closeOrder(order, now);
		return result;
	}

	public Integer getPriceForServices(Order order) {
		Integer result = orderWorker.getPriceForServices(order);
		return result;
	}

	public Order getActualOrder(Client client) {
		Order order = orderWorker.getActualOrder(client, now);
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
		if (roomWorker.getRooms().size() != 0 && clientWorker.getClients().size() != 0) {
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
		Saver.save(paths[0], orderWorker.toStringArray(orderWorker.getOrders()));
	}

	public void saveClients() {
		Saver.save(paths[1], clientWorker.toStringArray(clientWorker.getClients()));
	}

	public void saveRooms() {
		Saver.save(paths[2], roomWorker.toStringArray(roomWorker.getRooms()));
	}

	public void saveServices() {
		Saver.save(paths[3], serviceWorker.toStringArray(serviceWorker.getServices()));
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
