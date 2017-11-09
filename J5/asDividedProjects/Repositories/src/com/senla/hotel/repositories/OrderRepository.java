package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.senla.hotel.entities.*;
import com.senla.hotel.idcomparators.OrderIDComparator;

import utilities.IDGenerator;

public class OrderRepository implements IEntityRepository {
	private static TreeSet<Order> orders;

	public OrderRepository() {
		orders = new TreeSet<Order>(new OrderIDComparator());
	}

	@Override
	public Order getByID(Integer id) {
		for (Order order : orders) {
			if (order.getID().equals(id)) {
				return order;
			}
		}
		return null;
	}

	public Order[] searchByRoom(Room room) {
		ArrayList<Order> result = new ArrayList<>();
		orders.forEach((Order order) -> {
			if (order.getRoomID().equals(room.getID())) {
				result.add(order);
			}
		});
		return (Order[]) result.toArray(new Order[result.size()]);
	}

	public ArrayList<Order> searchByClient(Client client) {
		ArrayList<Order> result = new ArrayList<>();
		orders.forEach((Order order) -> {
			if (order.getClientID().equals(client.getID())) {
				result.add(order);
			}
		});
		return result;
	}
	
	public void sort(Comparator<Order> comparator) {
		List<Order> list = new ArrayList<Order>(orders);
		Collections.sort(list, comparator);
		orders.clear();
		orders.addAll(list);
	}

	public TreeSet<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(TreeSet<Order> orders) {
		OrderRepository.orders = orders;
	}

	@Override
	public int getCount() {
		return orders.size();
	}

	@Override
	public Boolean add(AEntity entity) {
		Boolean result = orders.add((Order) entity);
		if (result) {
			IDGenerator.addOrderID(entity.getID());
		}
		return result;
	}
}
