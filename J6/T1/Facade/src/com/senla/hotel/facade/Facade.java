package com.senla.hotel.facade;

import java.io.IOException;
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
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.enums.PropertyNames;
import com.senla.hotel.enums.RoomStatus;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectNameException;
import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.exceptions.NullException;
import com.senla.hotel.properties.HotelProperties;
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
	private HotelProperties properties;
	private static Facade instance;

	private Facade() {
		roomWorker = new RoomWorker();
		clientWorker = new ClientWorker();
		serviceWorker = new ServiceWorker();
		orderWorker = new OrderWorker();
		try {
			properties = HotelProperties.getInstance();
		} catch (IOException e) {
			LogWriter.getInstance().log(e, HotelProperties.class.getName());
		}
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

	public ArrayList<Order> getLastOrdersOfRoom(Room room) {
		try {
			ArrayList<Order> orders = orderWorker.getLastClients(room,
					Integer.parseInt(properties.getProperty(PropertyNames.ROOM_HISTORY_LENGTH.toString())));
			return orders;

		} catch (NumberFormatException | NullException e) {
			LogWriter.getInstance().log(e, "getLastOrdersOfRoom");
			return new ArrayList<Order>();
		}
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

	public ArrayList<Client> sortClientsByName(ArrayList<Client> clients) {
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
			serviceWorker.load(Loader.load(properties.getProperty(PropertyNames.SERVICE_FILENAME.toString())));
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullException | IncorrectIDEcxeption  e) {
			LogWriter.getInstance().log(e, "loadServices");
		}
	}

	public void loadRooms() {
		try {
			roomWorker.load(Loader.load(properties.getProperty(PropertyNames.ROOM_FILENAME.toString())));
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectParameterException | NullException  e) {
			LogWriter.getInstance().log(e, "loadRooms");
		}
	}

	public void loadClients() {
		try {
			clientWorker.load(Loader.load(properties.getProperty(PropertyNames.CLIENT_FILENAME.toString())));
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectNameException | NullException e) {
			LogWriter.getInstance().log(e, "loadClients");
		}
	}

	public void loadOrders() {
		if (roomWorker.getRooms().size() != 0 && clientWorker.getClients().size() != 0) {
			try {
				orderWorker.load(Loader.load(properties.getProperty(PropertyNames.ORDER_FILENAME.toString())));
			} catch (ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectIDEcxeption | NullException e) {
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
		try {
			Saver.save(properties.getProperty(PropertyNames.ORDER_FILENAME.toString()),
					orderWorker.toStringArray(orderWorker.getOrders()));
		} catch (NullException e) {
			LogWriter.getInstance().log(e, "saveOrders");
		}
	}

	public void saveClients() {
		try {
			String path = properties.getProperty(PropertyNames.CLIENT_FILENAME.toString());
			Saver.save(path, clientWorker.toStringArray(clientWorker.getClients()));
		} catch (NullException e) {
			LogWriter.getInstance().log(e, "saveClients");
		}
	}

	public void saveRooms() {
		try {
			Saver.save(properties.getProperty(PropertyNames.ROOM_FILENAME.toString()),
					roomWorker.toStringArray(roomWorker.getRooms()));
		} catch (NullException e) {
			LogWriter.getInstance().log(e, "saveRooms");
		}
	}

	public void saveServices() {
		try {
			Saver.save(properties.getProperty(PropertyNames.SERVICE_FILENAME.toString()),
					serviceWorker.toStringArray(serviceWorker.getServices()));
		} catch (NullException e) {
			LogWriter.getInstance().log(e, "saveServices");
		}
	}

	public void reset() {
		try {
			Saver.save(properties.getProperty(PropertyNames.ORDER_FILENAME.toString()), new String[] { "" });
			Saver.save(properties.getProperty(PropertyNames.CLIENT_FILENAME.toString()), new String[] { "" });
			Saver.save(properties.getProperty(PropertyNames.ROOM_FILENAME.toString()), new String[] { "" });
			Saver.save(properties.getProperty(PropertyNames.SERVICE_FILENAME.toString()), new String[] { "" });

		} catch (NullException e) {
			LogWriter.getInstance().log(e, "reset");
		}

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

	public void allowRoomStatusChangeAbility() {
		properties.setProperty(PropertyNames.CHANGE_ROOMSTATUS_ABILITY.toString(), "1");
	}

	public void denyRoomStatusChangeAbility() {
		properties.setProperty(PropertyNames.CHANGE_ROOMSTATUS_ABILITY.toString(), "0");
	}

	public Boolean setRoomStatus(Room room, RoomStatus status) {
		try {
			if (properties.getProperty(PropertyNames.CHANGE_ROOMSTATUS_ABILITY.toString()).equals("0")) {
				return false;
			}
		} catch (NullException e) {
			LogWriter.getInstance().log(e, "setRoomStatus");
		}
		room.setStatus(status);
		return false;
	}
}
