package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.senla.hotel.comparators.order.OrderDateComparator;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.enums.RoomStatus;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.repositories.ClientRepository;
import com.senla.hotel.repositories.OrderRepository;
import com.senla.hotel.repositories.RoomRepository;
import com.senla.hotel.repositories.ServiceRepository;

public class OrderWorker {
	private OrderRepository orderRepository;
	private ClientRepository clientRepository;
	private RoomRepository roomRepository;
	private ServiceRepository serviceRepository;

	public OrderWorker() {
		orderRepository = OrderRepository.getInstance();
		clientRepository = ClientRepository.getInstance();
		roomRepository = RoomRepository.getInstance();
		serviceRepository = ServiceRepository.getInstance();
	}

	private ArrayList<Client> makeClientList(ArrayList<Order> orders) {
		ArrayList<Client> clients = new ArrayList<Client>();
		for (Order order : orders) {
			clients.add(clientRepository.getByID(order.getClientID()));
		}
		return clients;
	}

	private ArrayList<Room> makeRoomList(ArrayList<Order> orders) {
		ArrayList<Room> rooms = new ArrayList<>();
		for (Order order : orders) {
			rooms.add(roomRepository.getByID(order.getRoomID()));
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
			if (!order.isActive(now) || roomRepository.getByID(order.getRoomID()).isOnService()) {
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
		return days * roomRepository.getByID(order.getRoomID()).getPricePerDay();
	}

	private Boolean isRoomAvailable(Order order) {
		int clientCount = 0;
		for (Order iterator : orderRepository.getOrders()) {
			if (order.compareDates(iterator) == 0 && order.getRoomID().equals(iterator.getRoomID())) {
				clientCount++;
			}
		}
		return clientCount < roomRepository.getByID(order.getRoomID()).getCapacity();
	}

	public Boolean add(Order order, Date date) throws IncorrectIDEcxeption {
		Room room = roomRepository.getByID(order.getRoomID());
		if (room == null || room.getStatus().equals(RoomStatus.ONSERVICE_NOW)
				|| clientRepository.getByID(order.getClientID()) == null
				|| !serviceRepository.checkServices(order.getServices())
				|| !isRoomAvailable(order)) {
			return false;
		}

		Boolean result = orderRepository.add(order);
		if (result && order.isActive(date)) {
			try {
				room.addClient(order.getClientID());
			} catch (IncorrectIDEcxeption e) {
				throw e;
			}
		}
		return result;
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
		return roomRepository.deleteClient(order.getRoomID(), order.getClientID());
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

	
	public void load(String path) throws ClassNotFoundException, IOException {
		orderRepository.load(path);
	}

	public void save(String path) throws IOException {
		orderRepository.save(path);
	}
}
