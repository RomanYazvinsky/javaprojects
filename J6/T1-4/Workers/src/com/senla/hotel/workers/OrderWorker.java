package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.comparators.order.OrderDateComparator;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.repositories.ClientRepository;
import com.senla.hotel.repositories.OrderRepository;
import com.senla.hotel.repositories.RoomRepository;
import com.senla.hotel.repositories.ServiceRepository;

import utilities.CSVWorker;
import utilities.Loader;
import utilities.Saver;

public class OrderWorker {
	private static Logger logger;
	private OrderRepository orderRepository;
	private ClientRepository clientRepository;
	private RoomRepository roomRepository;
	private ServiceRepository serviceRepository;
	static {
		logger = Logger.getLogger(OrderWorker.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	public OrderWorker() {
		orderRepository = OrderRepository.getInstance();
		clientRepository = ClientRepository.getInstance();
		roomRepository = RoomRepository.getInstance();
		serviceRepository = ServiceRepository.getInstance();
	}

	private ArrayList<Client> makeClientList(ArrayList<Order> orders) {
		ArrayList<Client> clients = new ArrayList<Client>();
		for (Order order : orders) {
			clients.add(clientRepository.getByID(order.getClientId()));
		}
		return clients;
	}

	private ArrayList<Room> makeRoomList(ArrayList<Order> orders) {
		ArrayList<Room> rooms = new ArrayList<>();
		for (Order order : orders) {
			rooms.add(roomRepository.getByID(order.getRoomId()));
		}
		return rooms;
	}

	public ArrayList<Order> sort(ArrayList<Order> list, Comparator<Order> comparator) {
		Collections.sort(list, comparator);
		return list;
	}

	public ArrayList<Order> getLastClients(Room room, int clientCount) {
		ArrayList<Order> lastOrders = new ArrayList<>();
		for (Order order : sort(orderRepository.searchByRoom(room), new OrderDateComparator())) {
			if (lastOrders.size() < clientCount) {
				lastOrders.add(order);
			}
		}
		return lastOrders;
	}

	public Order getActualOrder(Client client, Date now) {
		for (Order order : orderRepository.searchByClient(client)) {
			if (order.isActive(now)) {
				return order;
			}
		}
		return null;
	}

	public Order getOrderByID(Integer orderID) {
		if (orderID == null) {
			return null;
		}
		return orderRepository.getByID(orderID);
	}

	public ArrayList<Order> getActualOrders(Date now) {
		ArrayList<Order> actualOrders = new ArrayList<Order>(orderRepository.getOrders());
		for (Iterator<Order> i = actualOrders.iterator(); i.hasNext();) {
			Order order = i.next();
			if (!order.isActive(now) || roomRepository.getByID(order.getRoomId()).isOnService()) {
				i.remove();
			}
		}
		return actualOrders;
	}

	public ArrayList<Client> getActualClients(Date now) {
		return makeClientList(getActualOrders(now));
	}

	public Integer getActualClientCount(Date now) {
		return getActualClients(now).size();
	}

	public ArrayList<Room> getFreeRooms(Date date) {
		ArrayList<Room> usedRooms = makeRoomList(getActualOrders(date));
		ArrayList<Room> freeRooms = new ArrayList<Room>(roomRepository.getRooms());
		freeRooms.removeAll(usedRooms);
		for (Iterator<Room> i = freeRooms.iterator(); i.hasNext();) {
			if (i.next().isOnService()) {
				i.remove();
			}
		}
		return freeRooms;
	}

	public Integer getPriceForRoom(Order order) {
		if (order == null)
			return 0;
		long milliseconds = order.getOrderTo().getTime() - order.getOrderFrom().getTime();
		int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
		return days * roomRepository.getByID(order.getRoomId()).getPricePerDay();
	}

	private Boolean isRoomAvailable(Order order) {
		int clientCount = 0;
		for (Order iterator : orderRepository.getOrders()) {
			if (order.compareDates(iterator) == 0 && order.getRoomId().equals(iterator.getRoomId())) {
				clientCount++;
			}
		}
		return clientCount < roomRepository.getByID(order.getRoomId()).getCapacity();
	}

	public Boolean add(Order order, Date date) throws IncorrectIDEcxeption {
		Room room = roomRepository.getByID(order.getRoomId());
		if (room == null || room.getStatus().equals(RoomStatus.ONSERVICE_NOW)
				|| clientRepository.getByID(order.getClientId()) == null
				|| !serviceRepository.checkServices(order.getServices()) || !isRoomAvailable(order)) {
			return false;
		}

		Boolean result = orderRepository.add(order);
		if (result && order.isActive(date)) {
			try {
				room.addClient(order.getClientId());
			} catch (IncorrectIDEcxeption e) {
				throw e;
			}
		}
		return result;
	}

	public Boolean addNoIDGenerating(Order order) {
		return orderRepository.addNoIDGenerating(order);
	}

	public ArrayList<Service> getServicesOfClient(Client client) {
		ArrayList<Service> result = new ArrayList<Service>();
		for (Order order : getOrdersOfClient(client)) {
			result.addAll(order.getServices());
		}
		return result;
	}

	public Order getOrder(Integer id) {
		return orderRepository.getByID(id);
	}

	public ArrayList<Order> getOrders() {
		return orderRepository.getOrders();
	}

	public Boolean closeOrder(Order order, Date now) {
		order.setOrderTo(now);
		return roomRepository.deleteClient(order.getRoomId(), order.getClientId());
	}

	public ArrayList<Order> getOrdersOfClient(Client client) {
		ArrayList<Order> list = orderRepository.searchByClient(client);
		return list;
	}

	public Integer getPriceForServices(Order order) {
		Integer price = 0;
		if (order != null) {
			for (Service service : order.getServices()) {
				price += service.getPrice();
			}
		}
		return price;
	}

	public String[] toStringArray(ArrayList<Order> orders) {
		List<String> result = new ArrayList<>();
		for (Order order : orders) {
			result.add(order.toString());
		}
		return result.toArray(new String[result.size()]);
	}

	public void load(String path) throws ClassNotFoundException, IOException, EmptyObjectException {
		try {
			orderRepository.setOrders(Loader.loadOrders(path));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public void save(String path) throws IOException {
		try {
			Saver.saveOrders(path, orderRepository.getOrders());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public void export(Order order) {
		CSVWorker.exportOrder(order);
	}
	
	public void exportAll() {
		CSVWorker.exportOrders(orderRepository.getOrders());
	}

	public Boolean delete(Order order) {
		return orderRepository.delete(order);
	}
}
