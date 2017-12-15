package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
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
import com.senla.hotel.utilities.CSVModule;

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
			clients.add(order.getClient());
		}
		return clients;
	}

	private ArrayList<Room> makeRoomList(ArrayList<Order> orders) {
		ArrayList<Room> rooms = new ArrayList<>();
		for (Order order : orders) {
			rooms.add(order.getRoom());
		}
		return rooms;
	}

	public ArrayList<Order> selectByClient(Client client) {
		ArrayList<Order> result = new ArrayList<>();
		for (Order order : orderRepository.get()) {
			if (order.getClient().equals(client)) {
				result.add(order);
			}
		}
		return result;
	}

	public ArrayList<Order> selectByRoom(Room room) {
		ArrayList<Order> result = new ArrayList<>();
		for (Order order : orderRepository.get()) {
			if (order.getRoom().equals(room)) {
				result.add(order);
			}
		}
		return result;
	}

	public ArrayList<Order> sort(ArrayList<Order> list, Comparator<Order> comparator) {
		Collections.sort(list, comparator);
		return list;
	}

	public ArrayList<Order> getLastClientsOfRoom(Room room, int clientCount) {
		ArrayList<Order> lastOrders = new ArrayList<>();
		for (Order order : sort(selectByRoom(room), new OrderDateComparator())) {
			if (lastOrders.size() < clientCount) {
				lastOrders.add(order);
			}
		}
		return lastOrders;
	}

	public Order getActualOrder(Client client, Date now) {
		for (Order order : selectByClient(client)) {
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
		return (Order) orderRepository.getByID(orderID);
	}

	public ArrayList<Order> getActualOrders(Date now) {
		ArrayList<Order> actualOrders = new ArrayList<Order>(orderRepository.get());
		for (Iterator<Order> i = actualOrders.iterator(); i.hasNext();) {
			Order order = i.next();
			if (!order.isActive(now) || order.getRoom().isOnService()) {
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
		ArrayList<Room> freeRooms = new ArrayList<Room>(roomRepository.get());
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
		return days * order.getRoom().getPricePerDay();
	}

	private Boolean isRoomAvailable(Order order) {
		int clientCount = 0;
		for (Order iterator : orderRepository.get()) {
			if (order.compareDates(iterator) == 0 && order.getRoom().equals(iterator.getRoom())) {
				clientCount++;
			}
		}
		return clientCount < order.getRoom().getCapacity();
	}

	public Boolean add(Order order, Date date) throws IncorrectIDEcxeption {
		if (order.getRoom() == null || order.getRoom().getStatus().equals(RoomStatus.ONSERVICE_NOW)
				|| order.getClient() == null || !serviceRepository.checkServices(order.getServices())
				|| !isRoomAvailable(order) || !clientRepository.get().contains(order.getClient())
				|| !roomRepository.get().contains(order.getRoom())) {
			return false;
		}
		return orderRepository.add(order, true);
	}

	public Boolean add(Order order, boolean addId) {
		return orderRepository.add(order, addId);
	}

	public ArrayList<Service> getServicesOfClient(Client client) {
		ArrayList<Service> result = new ArrayList<Service>();
		for (Order order : selectByClient(client)) {
			result.addAll(order.getServices());
		}
		return result;
	}

	public Order getOrderById(Integer id) {
		return (Order) orderRepository.getByID(id);
	}

	public ArrayList<Order> getOrders() {
		return orderRepository.get();
	}

	public Boolean closeOrder(Order order, Date now) {
		order.setOrderTo(now);
		return true;
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
			orderRepository.set(Loader.loadOrders(path));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public void save(String path) throws IOException {
		try {
			Saver.saveOrders(path, orderRepository.get());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}
	public ArrayList<Order>  importAll() throws EmptyObjectException {
		ArrayList<Order> orders = new ArrayList<>();
		
		CSVModule.importAll(Order.class).forEach(new Consumer<Object>() {

			@Override
			public void accept(Object arg0) {
				Order order = (Order) arg0;
				ArrayList<Service> services = new ArrayList<>();
				order.setClient(clientRepository.getByID(order.getClient().getId()));
				order.setRoom(roomRepository.getByID(order.getRoom().getId()));
				for (int i = 0; i < order.getServices().size(); i++) {
					services.add(serviceRepository.getByID(order.getServices().get(i).getId()));
				}
				order.setServices(services);
				orders.add(order);
			}
		});
		return orders;
	}
	public void exportAll() {
		CSVModule.exportAll(getOrders());
	}

	public Boolean delete(Order order) {
		return orderRepository.delete(order);
	}
}
