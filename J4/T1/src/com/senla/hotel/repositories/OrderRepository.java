package com.senla.hotel.repositories;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;

import utilities.ArrayWorker;
import utilities.IDGenerator;

public class OrderRepository implements IEntityRepository {
	private static Order[] orders;

	public OrderRepository() {
		orders = new Order[6];
	}

	@Override
	public Order getByID(Integer id) {
		for (Order order : orders) {
			if (order != null) {
				if (order.getID().equals(id)) {
					return order;
				}
			}
		}
		return null;
	}

	public int searchByRoom(Room room, int startIndex) {
		for (int i = startIndex; i < ArrayWorker.getCount(orders); i++) {
			if (orders[i].getRoomID().equals(room.getID()))
				return i;
		}
		return -1;
	}

	public int searchByClient(Client client, int startIndex) {
		for (int i = startIndex; i < ArrayWorker.getCount(orders); i++) {
			if (orders[i].getClientID().equals(client.getID()))
				return i;
		}
		return -1;
	}

	@Override
	public int getIndex(AEntity entity) {
		for (int i = 0; i < ArrayWorker.getCount(orders); i++) {
			if (orders[i].equals(entity) && orders[i].compareDates((Order) entity) == 0) {
				return i;
			}
		}
		return -1;
	}

	public Order[] getOrders() {
		return orders;
	}

	@Override
	public int getCount() {
		return ArrayWorker.getCount(orders);
	}

	@Override
	public Boolean add(AEntity entity) {
		int orderCount = ArrayWorker.getCount(orders);
		if (getIndex(entity) >= 0 && orderCount > 0) {
			return false;
		}
		if (orderCount == orders.length) {
			orders = (Order[]) ArrayWorker.extendEntityArray(orders);
		}
		orders[orderCount] = (Order) entity;
		IDGenerator.addOrderID(entity.getID());
		return true;
	}
}
