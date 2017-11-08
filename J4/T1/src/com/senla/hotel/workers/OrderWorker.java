package com.senla.hotel.workers;

import java.util.Arrays;
import java.util.GregorianCalendar;

import com.senla.hotel.comparators.order.OrderClientNameComparator;
import com.senla.hotel.comparators.order.OrderDateComparator;
import com.senla.hotel.comparators.service.ServiceDateComparator;
import com.senla.hotel.comparators.service.ServiceNameComparator;
import com.senla.hotel.comparators.service.ServicePriceComparator;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.RoomStatus;
import com.senla.hotel.entities.Service;
import com.senla.hotel.repositories.ClientRepository;
import com.senla.hotel.repositories.OrderRepository;
import com.senla.hotel.repositories.RoomRepository;
import com.senla.hotel.repositories.ServiceRepository;

import utilities.ArrayWorker;

public class OrderWorker {
	private OrderRepository orderRepository;
	private ClientRepository clientManager;
	private RoomRepository roomRepository;
	private ServiceRepository serviceRepository;
	public OrderWorker() {
		orderRepository = new OrderRepository();
		clientManager = new ClientRepository();
		roomRepository = new RoomRepository();
		serviceRepository = new ServiceRepository();
	}

	

	private Client[] makeClientArray(Order[] orders) {
		Client[] clients = new Client[ArrayWorker.getCount(orders)];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = clientManager.getByID(orders[i].getClientID());
		}
		return clients;
	}

	private Room[] makeRoomArray(Order[] orders) {
		Room[] rooms = new Room[ArrayWorker.getCount(orders)];
		for (int i = 0; i < rooms.length; i++) {
			rooms[i] = roomRepository.getByID(orders[i].getRoomID());
		}
		return rooms;
	}

	private void sortByName() {
		Arrays.sort(orderRepository.getOrders(), new OrderClientNameComparator());
	}

	private void sortByDate() {
		Arrays.sort(orderRepository.getOrders(), new OrderDateComparator());
	}


	private void sortServicesByPrice(Service[] services) {
		Arrays.sort(services, new ServicePriceComparator());
	}

	private void sortServicesByDate(Service[] services) {
		Arrays.sort(services, new ServiceDateComparator());
	}

	private void sortServicesByName(Service[] services) {
		Arrays.sort(services, new ServiceNameComparator());
	}
	
	
	public Order[] getLastClients(Room room, int clientCount) {
		sortByDate();
		Order[] lastOrders = new Order[clientCount];
		for (int i = 0, startIndex = orderRepository.searchByRoom(room, 0); startIndex >= 0
				&& i < clientCount; startIndex = orderRepository.searchByRoom(room, startIndex), i++) {
			lastOrders[i] = orderRepository.getOrders()[startIndex++];
		}
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(lastOrders));
	}

	public Order getActualOrder(Client client, GregorianCalendar now) {
		int tempIndex = orderRepository.searchByClient(client, 0);
		while (tempIndex >= 0) {
			if (orderRepository.getOrders()[tempIndex].isActive(now)) {
				return orderRepository.getOrders()[tempIndex];
			}
			tempIndex = orderRepository.searchByClient(client, tempIndex);
		}
		return null;
	}

	public Order[] getOrdersSortedByClientName() {
		sortByName();
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(orderRepository.getOrders()));
	}

	public Order[] getOrderSortedByDate() {
		sortByDate();
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(orderRepository.getOrders()));
	}

	public Order getOrderByID(Integer orderID) {
		return orderRepository.getByID(orderID);
	}

	public Order[] getActualOrders(GregorianCalendar now) {
		Order[] actualOrders = new Order[orderRepository.getCount()];
		for (int i = 0, k = 0; i < actualOrders.length; i++) {
			if (orderRepository.getOrders()[i].isActive(now)) {
				actualOrders[k++] = orderRepository.getOrders()[i];
			}
		}
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(actualOrders));
	}

	public Client[] getActualClients(GregorianCalendar now) {
		return makeClientArray(getActualOrders(now));
	}

	public Integer getActualClientCount(GregorianCalendar now) {
		return getActualClients(now).length;
	}

	public Room[] getFreeRoomByDate(GregorianCalendar date) {
		Room[] usedRooms = makeRoomArray(getActualOrders(date));
		Room[] freeRooms = new Room[roomRepository.getCount()];
		Boolean isFound;
		int j = 0;
		for (int i = 0; i < roomRepository.getCount(); i++) {
			isFound = false;
			for (int k = 0; k < usedRooms.length; k++) {
				if (usedRooms[k].equals(roomRepository.getRooms()[i])) {
					isFound = true;
					break;
				}
			}
			if (!isFound && !roomRepository.getRooms()[i].getStatus().equals(RoomStatus.ONSERVICE)) {
				freeRooms[j++] = roomRepository.getRooms()[i];
			}
		}
		return ArrayWorker.castToRoom(ArrayWorker.decreaseArray(freeRooms));
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
				|| clientManager.getByID(order.getClientID()) == null) {
			return false;
		}
		if (order.getServices() != null) {
			for(Service service : order.getServices()) {
				if (service != null && serviceRepository.getIndex(service) < 0) {
					return false;
				}
			}
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
		Service[] result = null;
		for (Order order : getOrdersOfClient(client)) {
			if (result == null) {
				result = order.getServices();
			} else {
				result = ArrayWorker.addUp(result, order.getServices());
			}
		}
		return result;
	}

	public Order getOrder(Integer id) {
		return orderRepository.getByID(id);
	}

	public Order[] getOrders() {
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(orderRepository.getOrders()));
	}

	public Boolean closeOrder(Order order, GregorianCalendar now) {
		order.setOrderTo(now);
		return roomRepository.deleteClient(order.getRoomID(), order.getClientID());
	}

	public Order[] getOrdersOfClient(Client client) {
		int size = orderRepository.getCount();
		Order[] ordersOfClient = new Order[size];
		for (int i = 0, k = 0; i < size; i++) {
			if (client.getID().equals(orderRepository.getOrders()[i].getClientID())) {
				ordersOfClient[k++] = orderRepository.getOrders()[i];
			}
		}
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(ordersOfClient));
	}

	public Service[] getServicesSortedByPrice(Order order) {
		sortServicesByPrice(order.getServices());
		return order.getServices();
	}

	public Service[] getServicesSortedByDate(Order order) {
		sortServicesByDate(order.getServices());
		return order.getServices();
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
		Service[] services = order.getServices();
		if (services == null) {
			return 0;
		}
		for (Service service : services ) {
			if (service != null) {
				price += service.getPrice();
			}
		}
		return price;
	}

	public String[] makeWriteableArray() {
		int count = orderRepository.getCount();
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = orderRepository.getOrders()[i].toString();
		}
		return result;
	}

}
