package com.senla.hotel.facade;

import java.util.ArrayList;
import java.util.Date;

import com.senla.hotel.comparators.client.ClientNameComparator;
import com.senla.hotel.comparators.order.OrderClientNameComparator;
import com.senla.hotel.comparators.order.OrderDateComparator;
import com.senla.hotel.comparators.room.RoomCapacityComparator;
import com.senla.hotel.comparators.room.RoomPriceComparator;
import com.senla.hotel.comparators.room.RoomStarComparator;
import com.senla.hotel.comparators.service.ServiceDateComparator;
import com.senla.hotel.comparators.service.ServiceNameComparator;
import com.senla.hotel.comparators.service.ServicePriceComparator;
import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectNameException;
import com.senla.hotel.workers.ClientWorker;
import com.senla.hotel.workers.OrderWorker;
import com.senla.hotel.workers.RoomWorker;
import com.senla.hotel.workers.ServiceWorker;

import utilities.Loader;
import utilities.LogWriter;
import utilities.Saver;

public class Facade {
	private RoomWorker roomWorker;
	private ClientWorker clientWorker;
	private ServiceWorker serviceWorker;
	private OrderWorker orderWorker;
	private static Facade instance;

	private Facade() {
		Parameters.setPaths(new String[] { "orders.txt", "clients.txt", "rooms.txt", "services.txt" });
		roomWorker = new RoomWorker();
		clientWorker = new ClientWorker();
		serviceWorker = new ServiceWorker();
		orderWorker = new OrderWorker();
	}
	
	public static Facade getInstance() {
		if (instance == null) {
			instance = new Facade();
		}
		return instance;
	}

	public Boolean addClient(Client client) {
		Boolean result = clientWorker.add(client);
		return result;
	}

	public Boolean addOrder(Order order, Date date) {
		Boolean result;
		try {
			result = orderWorker.add(order, date);
			return result;
		} catch (IncorrectIDEcxeption e) {
			LogWriter.getInstance().log(e, "addOrder");
			return false;
		}
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

	public ArrayList<Order> getActualOrders(Date date) {
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

	public Integer getFreeRoomsCount(Date date) {
		Integer result = orderWorker.getFreeRooms(date).size();
		return result;
	}

	public Integer getActualClientCount(Date date) {
		Integer result = orderWorker.getActualClientCount(date);
		return result;
	}

	public ArrayList<Room> getFreeRooms(Date date) {
		ArrayList<Room> rooms = orderWorker.getFreeRooms(date);
		return rooms;
	}

	public Integer getPriceForRoom(Order order) {
		Integer result = orderWorker.getPriceForRoom(order);
		return result;
	}

	public ArrayList<Order> getLastOrdersOfRoom(Room room, Integer clientCount) {
		ArrayList<Order> orders = orderWorker.getLastClients(room, clientCount);
		return orders;
	}
	
	public Integer getPriceForServices(ArrayList<Service> services) {
		return serviceWorker.getPriceForServices(services);
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

	public ArrayList<Client> sortClientsByName(ArrayList<Client> clients){
		return clientWorker.sort(clients, new ClientNameComparator());
	}
	
	public ArrayList<Service> getServices() {
		ArrayList<Service> services = serviceWorker.getServices();
		return services;
	}

	public Boolean closeOrder(Order order, Date now) {
		Boolean result = orderWorker.closeOrder(order, now);
		return result;
	}

	public Integer getPriceForServices(Order order) {
		Integer result = orderWorker.getPriceForServices(order);
		return result;
	}

	public Order getActualOrder(Client client, Date now) {
		Order order = orderWorker.getActualOrder(client, now);
		return order;
	}

	public void loadServices() {
		try {
			serviceWorker.load(Loader.load(Parameters.getPaths()[3]));
		} catch (Exception e) {
			LogWriter.getInstance().log(e, "loadServices");
		}
	}

	public void loadRooms() {
		try {
			roomWorker.load(Loader.load(Parameters.getPaths()[2]));
		} catch (Exception e) {
			LogWriter.getInstance().log(e, "loadRooms");
		}
	}

	public void loadClients() {
		try {
			clientWorker.load(Loader.load(Parameters.getPaths()[1]));
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectNameException e) {
			LogWriter.getInstance().log(e, "loadClients");
		}
	}

	public void loadOrders() {
		if (roomWorker.getRooms().size() != 0 && clientWorker.getClients().size() != 0) {
			try {
				orderWorker.load(Loader.load(Parameters.getPaths()[0]));
			} catch (ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectIDEcxeption e) {
				LogWriter.getInstance().log(e, "loadOrders");
			}
		}
	}

	public void load() {
		loadServices();
		loadRooms();
		loadClients();
		loadOrders();
	}

	public void saveOrders() {
		Saver.save(Parameters.getPaths()[0], orderWorker.toStringArray(orderWorker.getOrders()));
	}

	public void saveClients() {
		Saver.save(Parameters.getPaths()[1], clientWorker.toStringArray(clientWorker.getClients()));
	}

	public void saveRooms() {
		Saver.save(Parameters.getPaths()[2], roomWorker.toStringArray(roomWorker.getRooms()));
	}

	public void saveServices() {
		Saver.save(Parameters.getPaths()[3], serviceWorker.toStringArray(serviceWorker.getServices()));
	}

	public void reset() {
		Saver.save(Parameters.getPaths()[0], new String[] { "" });
		Saver.save(Parameters.getPaths()[1], new String[] { "" });
		Saver.save(Parameters.getPaths()[2], new String[] { "" });
		Saver.save(Parameters.getPaths()[3], new String[] { "" });

	}

	public void save() {
		saveOrders();
		saveClients();
		saveRooms();
		saveServices();
	}

	public ArrayList<Client> getActualClients(Date date) {
		return orderWorker.getActualClients(date);
	}
}
