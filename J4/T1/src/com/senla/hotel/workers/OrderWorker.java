package com.senla.hotel.workers;

import java.util.Arrays;
import java.util.GregorianCalendar;

import com.senla.hotel.comparators.OrderComparator;
import com.senla.hotel.comparators.ServiceComparator;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.RoomStatus;
import com.senla.hotel.entities.Service;
import com.senla.hotel.managers.ClientManager;
import com.senla.hotel.managers.OrderManager;
import com.senla.hotel.managers.RoomManager;
import com.senla.hotel.managers.ServiceManager;
import com.senla.hotel.utilities.ArrayWorker;

public class OrderWorker {
	private OrderManager orderManager;
	private ClientManager clientManager;
	private RoomManager roomManager;
	private OrderComparator orderComparator;
	private ServiceComparator serviceComparator;
	private ServiceManager serviceManager;
	public OrderWorker() {
		orderManager = new OrderManager();
		clientManager = new ClientManager();
		roomManager = new RoomManager();
		orderComparator = new OrderComparator();
		serviceComparator = new ServiceComparator();
		serviceManager = new ServiceManager();
	}

	public Boolean add(Order order) {
		Room room = roomManager.getRoomByID(order.getRoomID());
		if (room == null || room.getStatus().equals(RoomStatus.USED) || room.getStatus().equals(RoomStatus.ONSERVICE)
				|| clientManager.getClientByID(order.getClientID()) == null) {
			return false;
		}
		if (order.getServices() != null) {
			for(Service service : order.getServices()) {
				if (service != null && serviceManager.getServiceByID(service.getID()) == null) {
					return false;
				}
			}
		}
		Boolean result = orderManager.add(order);
		if (result) {
			roomManager.getRoomByID(order.getRoomID()).addClient(order.getClientID());
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
		return orderManager.getOrderByID(id);
	}

	public Order[] getOrders() {
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(orderManager.getOrders()));
	}

	public Boolean closeOrder(Order order, GregorianCalendar now) {
		order.setOrderTo(now);
		return roomManager.deleteClient(order.getRoomID(), order.getClientID());
	}

	public Order[] getOrdersOfClient(Client client) {
		int size = orderManager.getCount();
		Order[] ordersOfClient = new Order[size];
		for (int i = 0, k = 0; i < size; i++) {
			if (client.getID().equals(orderManager.getOrders()[i].getClientID())) {
				ordersOfClient[k++] = orderManager.getOrders()[i];
			}
		}
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(ordersOfClient));
	}

	private Client[] makeClientArray(Order[] orders) {
		Client[] clients = new Client[ArrayWorker.getCount(orders)];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = clientManager.getClientByID(orders[i].getClientID());
		}
		return clients;
	}

	private Room[] makeRoomArray(Order[] orders) {
		Room[] rooms = new Room[ArrayWorker.getCount(orders)];
		for (int i = 0; i < rooms.length; i++) {
			rooms[i] = roomManager.getRoomByID(orders[i].getRoomID());
		}
		return rooms;
	}

	private void sortByName() {
		Arrays.sort(orderManager.getOrders(), orderComparator.nameComparator);
	}

	private void sortByDate() {
		Arrays.sort(orderManager.getOrders(), orderComparator.dateComparator);
	}

	public Order[] getLastClients(Room room, int clientCount) {
		sortByDate();
		Order[] lastOrders = new Order[clientCount];
		for (int i = 0, startIndex = orderManager.searchByRoom(room, 0); startIndex >= 0
				&& i < clientCount; startIndex = orderManager.searchByRoom(room, startIndex), i++) {
			lastOrders[i] = orderManager.getOrders()[startIndex++];
		}
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(lastOrders));
	}

	public Order getActualOrder(Client client, GregorianCalendar now) {
		int tempIndex = orderManager.searchByClient(client, 0);
		while (tempIndex >= 0) {
			if (orderManager.getOrders()[tempIndex].isActive(now)) {
				return orderManager.getOrders()[tempIndex];
			}
			tempIndex = orderManager.searchByClient(client, tempIndex);
		}
		return null;
	}

	public Order[] getOrdersSortedByClientName() {
		sortByName();
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(orderManager.getOrders()));
	}

	public Order[] getOrderSortedByDate() {
		sortByDate();
		return ArrayWorker.castToOrder(ArrayWorker.decreaseArray(orderManager.getOrders()));
	}

	public Order getOrderByID(Integer orderID) {
		return orderManager.getOrderByID(orderID);
	}

	public Order[] getActualOrders(GregorianCalendar now) {
		Order[] actualOrders = new Order[orderManager.getCount()];
		for (int i = 0, k = 0; i < actualOrders.length; i++) {
			if (orderManager.getOrders()[i].isActive(now)) {
				actualOrders[k++] = orderManager.getOrders()[i];
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
		Room[] freeRooms = new Room[roomManager.getCount()];
		Boolean isFound;
		int j = 0;
		for (int i = 0; i < roomManager.getCount(); i++) {
			isFound = false;
			for (int k = 0; k < usedRooms.length; k++) {
				if (usedRooms[k].equals(roomManager.getRooms()[i])) {
					isFound = true;
					break;
				}
			}
			if (!isFound && !roomManager.getRooms()[i].getStatus().equals(RoomStatus.ONSERVICE)) {
				freeRooms[j++] = roomManager.getRooms()[i];
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
		return days * roomManager.getRoomByID(order.getRoomID()).getPricePerDay();
	}

	private void sortServicesByPrice(Service[] services) {
		Arrays.sort(services, serviceComparator.priceComparator);
	}

	private void sortServicesByDate(Service[] services) {
		Arrays.sort(services, serviceComparator.dateComparator);
	}

	private void sortServicesByName(Service[] services) {
		Arrays.sort(services, serviceComparator.nameComparator);
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
		int count = orderManager.getCount();
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = orderManager.getOrders()[i].toString();
		}
		return result;
	}

}
