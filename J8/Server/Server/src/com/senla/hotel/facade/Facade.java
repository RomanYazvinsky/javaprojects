package com.senla.hotel.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IClientWorker;
import com.senla.hotel.api.internal.IOrderWorker;
import com.senla.hotel.api.internal.IRoomWorker;
import com.senla.hotel.api.internal.IServiceWorker;
import com.senla.hotel.comparators.client.ClientNameComparator;
import com.senla.hotel.comparators.order.OrderClientNameComparator;
import com.senla.hotel.comparators.order.OrderDateComparator;
import com.senla.hotel.comparators.room.RoomCapacityComparator;
import com.senla.hotel.comparators.room.RoomPriceComparator;
import com.senla.hotel.comparators.room.RoomStarComparator;
import com.senla.hotel.comparators.service.ServiceDateComparator;
import com.senla.hotel.comparators.service.ServiceNameComparator;
import com.senla.hotel.comparators.service.ServicePriceComparator;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.PropertyNames;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.properties.HotelProperties;

import utilities.DependencyInjector;

public class Facade {
	private static Logger logger;
	private IRoomWorker roomWorker;
	private IClientWorker clientWorker;
	private IServiceWorker serviceWorker;
	private IOrderWorker orderWorker;
	private HotelProperties properties;
	private static Facade instance;

	static {
		logger = Logger.getLogger(Facade.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	private Facade() {
		roomWorker = (IRoomWorker) DependencyInjector.newInstance(IRoomWorker.class);
		clientWorker = (IClientWorker) DependencyInjector.newInstance(IClientWorker.class);
		serviceWorker = (IServiceWorker) DependencyInjector.newInstance(IServiceWorker.class);
		orderWorker = (IOrderWorker)DependencyInjector.newInstance(IOrderWorker.class);
		try {
			properties = HotelProperties.getInstance(Constants.PATH_TO_PROPERTIES);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static Facade getInstance() {
		if (instance == null) {
			instance = new Facade();
		}
		return instance;
	}

	public Boolean addClient(Client client) {
		Boolean result = clientWorker.add(client, true);
		return result;
	}

	public Boolean addOrder(Order order, Date date) {
		Boolean result;
		try {
			result = orderWorker.add(order, date);
			return result;
		} catch (IncorrectIDEcxeption e) {
			logger.log(Level.SEVERE, e.getMessage());
			return false;
		}
	}

	public Boolean addRoom(Room room) {
		Boolean result = roomWorker.add(room, true);
		return result;
	}

	public Boolean addService(Service service) {
		Boolean result = serviceWorker.add(service, true);
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
			ArrayList<Order> orders = orderWorker.getLastClientsOfRoom(room,
					Integer.parseInt(properties.getProperty(PropertyNames.ROOM_HISTORY_LENGTH.toString())));
			return orders;

		} catch (NumberFormatException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
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
			serviceWorker.load(properties.getProperty(PropertyNames.SERVICE_FILENAME.toString()));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void loadRooms() {
		try {
			roomWorker.load(properties.getProperty(PropertyNames.ROOM_FILENAME.toString()));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void loadClients() {
		try {
			clientWorker.load(properties.getProperty(PropertyNames.CLIENT_FILENAME.toString()));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void loadOrders() {
		try {
			orderWorker.load(properties.getProperty(PropertyNames.ORDER_FILENAME.toString()));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
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
			orderWorker.save(properties.getProperty(PropertyNames.ORDER_FILENAME.toString()));
		} catch (IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void saveClients() {
		try {
			clientWorker.save(properties.getProperty(PropertyNames.CLIENT_FILENAME.toString()));
		} catch (IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void saveRooms() {
		try {
			roomWorker.save(properties.getProperty(PropertyNames.ROOM_FILENAME.toString()));
		} catch (IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void saveServices() {
		try {
			serviceWorker.save(properties.getProperty(PropertyNames.SERVICE_FILENAME.toString()));
		} catch (IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
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

	public Boolean setRoomStatus(Room room, RoomStatus status) {
		try {
			if (properties.getProperty(PropertyNames.CHANGE_ROOMSTATUS_ABILITY.toString()).equals("0")) {
				return false;
			}
		} catch (EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		room.setStatus(status);
		return false;
	}

	public ArrayList<Client> importClients() {
		try {
			return clientWorker.importAll();
		} catch (EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}

	public ArrayList<Order> importOrders() {
		try {
			return orderWorker.importAll();
		} catch (EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}

	public ArrayList<Room> importRooms() {
		try {
			return roomWorker.importAll();
		} catch (EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}

	public ArrayList<Service> importServices() {
		try {
			return serviceWorker.importAll();
		} catch (EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}

	public void exportClients() {
		clientWorker.exportAll();
	}

	public void exportOrders() {
		orderWorker.exportAll();
	}

	public void exportRooms() {
		roomWorker.exportAll();
	}

	public void exportServices() {
		serviceWorker.exportAll();
	}

	public Boolean deleteClient(Client client) {
		return clientWorker.delete(client);
	}

	public Boolean deleteOrder(Order order) {
		return orderWorker.delete(order);
	}

	public Boolean deleteRoom(Room room) {
		return roomWorker.delete(room);
	}

	public Boolean deleteService(Service service) {
		return serviceWorker.delete(service);
	}

	public Boolean addClientWithID(Client client) {
		return clientWorker.add(client, false);
	}

	public Boolean addRoomWithID(Room room) {
		return roomWorker.add(room, false);
	}

	public Boolean addOrderWithID(Order order) {
		return orderWorker.add(order, false);
	}

	public Boolean addServiceWithID(Service service) {
		return serviceWorker.add(service, false);
	}


}
