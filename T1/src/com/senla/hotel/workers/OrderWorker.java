package com.senla.hotel.workers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.senla.hotel.comparators.order.*;
import com.senla.hotel.comparators.service.*;
import com.senla.hotel.entities.*;
import com.senla.hotel.repositories.*;

public class OrderWorker {
	private OrderRepository orderRepository;
	private ClientRepository clientRepository;
	private RoomRepository roomRepository;
	private ServiceRepository serviceRepository;

	public OrderWorker() {
		orderRepository = new OrderRepository();
		clientRepository = new ClientRepository();
		roomRepository = new RoomRepository();
		serviceRepository = new ServiceRepository();
	}

	private Client[] makeClientArray(Order[] orders) {
		ArrayList<Client> clients = new ArrayList<>();
		for (Order order : orders) {
			clients.add(clientRepository.getByID(order.getClientID()));
		}
		return clients.toArray(new Client[clients.size()]);
	}

	private Room[] makeRoomArray(Order[] orders) {
		ArrayList<Room> rooms = new ArrayList<>();
		for (Order order : orders) {
			rooms.add(roomRepository.getByID(order.getRoomID()));
		}
		return rooms.toArray(new Room[rooms.size()]);
	}

	public void sortByName() {
		orderRepository.sort(new OrderClientNameComparator());
	}

	public void sortByDate() {
		orderRepository.sort(new OrderDateComparator());
	}

	private void sortServicesByPrice(ArrayList<Service> services) {
		Collections.sort(services, new ServicePriceComparator());
	}

	private void sortServicesByPrice(Service[] services) {
		Arrays.sort(services, new ServicePriceComparator());
	}

	private void sortServicesByDate(ArrayList<Service> services) {
		Collections.sort(services, new ServiceDateComparator());
	}

	private void sortServicesByDate(Service[] services) {
		Arrays.sort(services, new ServiceDateComparator());
	}

	private void sortServicesByName(Service[] services) {
		Arrays.sort(services, new ServiceNameComparator());
	}

	public Order[] getLastClients(Room room, int clientCount) {
		sortByDate();
		ArrayList<Order> lastOrders = new ArrayList<>();
		for (Order order : orderRepository.searchByRoom(room)) {
			if (lastOrders.size() < clientCount) {
				lastOrders.add(order);
			}
		}
		return lastOrders.toArray(new Order[lastOrders.size()]);
	}

	public Order getActualOrder(Client client, GregorianCalendar now) {
		for (Order order : orderRepository.searchByClient(client)) {
			if (order.isActive(now)) {
				return order;
			}
		}
		return null;
	}

	public Order[] getOrdersSortedByClientName() {
		sortByName();
		return orderRepository.getOrders().toArray(new Order[orderRepository.getCount()]);
	}

	public Order[] getOrderSortedByDate() {
		sortByDate();
		return (Order[]) orderRepository.getOrders().toArray(new Order[orderRepository.getCount()]);
	}

	public Order getOrderByID(Integer orderID) {
		return orderRepository.getByID(orderID);
	}

	public Order[] getActualOrders(GregorianCalendar now) {
		Set<Order> actualOrders = orderRepository.getOrders();
		for (Iterator<Order> i = actualOrders.iterator(); i.hasNext();) {
			if (!i.next().isActive(now)) {
				i.remove();
			}
		}
		return actualOrders.toArray(new Order[actualOrders.size()]);
	}

	public Client[] getActualClients(GregorianCalendar now) {
		return makeClientArray(getActualOrders(now));
	}

	public Integer getActualClientCount(GregorianCalendar now) {
		return getActualClients(now).length;
	}

	public Room[] getFreeRoomByDate(GregorianCalendar date) {
		Room[] usedRooms = makeRoomArray(getActualOrders(date));
		List<Room> freeRooms = new ArrayList<Room>(roomRepository.getRooms());
		for (Room room : usedRooms) {
			freeRooms.remove(room);
		}
		return freeRooms.toArray(new Room[freeRooms.size()]);
	}

	public Integer getPriceForRoom(Client client, GregorianCalendar now) {
		Order order = getActualOrder(client, now);
		if (order == null)
			return 0;
		long milliseconds = now.getTimeInMillis() - getActualOrder(client, now).getOrderFrom().getTimeInMillis();
		int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
		return days * roomRepository.getByID(order.getRoomID()).getPricePerDay();
	}

	public Boolean add(Order order) {
		Room room = roomRepository.getByID(order.getRoomID());
		if (room == null || room.getStatus().equals(RoomStatus.USED) || room.getStatus().equals(RoomStatus.ONSERVICE)
				|| clientRepository.getByID(order.getClientID()) == null
				|| !serviceRepository.checkServices(order.getServices())) {
			return false;
		}

		Boolean result = orderRepository.add(order);
		if (result) {
			roomRepository.getByID(order.getRoomID()).addClient(order.getClientID());
		}
		return result;
	}

	public void load(String[] orderData) {
		for (String data : orderData) {
			if (data.compareTo("") != 0) {
				add(new Order(data));
			}
		}
	}

	public Service[] getServicesOfClient(Client client) {
		List<Service> result = new ArrayList<Service>();
		for (Order order : getOrdersOfClient(client)) {
			result.addAll(order.getServices());
		}
		return result.toArray(new Service[result.size()]);
	}

	public Order getOrder(Integer id) {
		return orderRepository.getByID(id);
	}

	public Order[] getOrders() {
		return orderRepository.getOrders().toArray(new Order[orderRepository.getCount()]);
	}

	public Boolean closeOrder(Order order, GregorianCalendar now) {
		order.setOrderTo(now);
		return roomRepository.deleteClient(order.getRoomID(), order.getClientID());
	}

	public Order[] getOrdersOfClient(Client client) {
		ArrayList<Order> list = orderRepository.searchByClient(client);
		return list.toArray(new Order[list.size()]);
	}

	public Service[] getServicesSortedByPrice(Order order) {
		sortServicesByPrice(order.getServices());
		return order.getServices().toArray(new Service[order.getServiceCount()]);
	}

	public Service[] getServicesSortedByDate(Order order) {
		sortServicesByDate(order.getServices());
		return order.getServices().toArray(new Service[order.getServiceCount()]);
	}

	public Service[] getServicesSortedByPrice(Client client) {
		Service[] result = getServicesOfClient(client);
		sortServicesByPrice(result);
		return result;
	}

	public Service[] getServicesSortedByDate(Client client) {
		Service[] result = getServicesOfClient(client);
		sortServicesByDate(result);
		return result;
	}

	public Service[] getServicesSortedByName(Client client) {
		Service[] result = getServicesOfClient(client);
		sortServicesByName(result);
		return result;
	}

	public Integer getPriceForServices(Order order) {
		Integer price = 0;
		if (order == null) {
			return 0;
		}
		for (Service service : order.getServices()) {
			price += service.getPrice();
		}
		return price;
	}

	public String[] makeWriteableArray() {
		List<String> result = new ArrayList<>();
		for (Order order : orderRepository.getOrders()) {
			result.add(order.toString());
		}
		return result.toArray(new String[result.size()]);
	}

}
