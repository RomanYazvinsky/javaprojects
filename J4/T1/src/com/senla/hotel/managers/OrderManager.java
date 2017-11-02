package com.senla.hotel.managers;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.utilities.ArrayWorker;

public class OrderManager extends AEntityManager {
	private static Order[] orders;

	public OrderManager() {
		super();
		if (OrderManager.orders == null) {
			orders = new Order[6];
			entities = OrderManager.orders;
		} else {
			OrderManager.orders = ArrayWorker.castToOrder(entities);
		}
	}

	public Order getOrderByID(Integer id) {
		return (Order) getByID(id);
	}

	public int searchByRoom(Room room, int startIndex) {
		for (int i = startIndex; i < ArrayWorker.getCount(getOrders()); i++) {
			if (getOrders()[i].getRoomID().equals(room.getID()))
				return i;
		}
		return -1;
	}

	public int searchByClient(Client client, int startIndex) {
		for (int i = startIndex; i < ArrayWorker.getCount(getOrders()); i++) {
			if (getOrders()[i].getClientID().equals(client.getID()))
				return i;
		}
		return -1;
	}

	@Override
	protected int find(AEntity entity) {
		for (int i = 0; i < ArrayWorker.getCount(entities); i++) {
			if (orders[i].equals(entity) && orders[i].compareDates((Order) entity) == 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	protected Boolean isCorrectType(AEntity entity) {
		return (entity instanceof Order);
	}

	@Override
	protected void extendArray() {
		OrderManager.orders = (Order[]) ArrayWorker.extendEntityArray(orders);
		entities = OrderManager.orders;
	}

	public Order[] getOrders() {
		return OrderManager.orders;
	}

	@Override
	public int getCount() {
		return ArrayWorker.getCount(OrderManager.orders);
	}
}
