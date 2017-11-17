package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.TreeSet;

import com.senla.hotel.entities.*;

import utilities.IDGenerator;

public class OrderRepository implements IEntityRepository {
	private TreeSet<Order> orders;
	private static OrderRepository instance;

	private OrderRepository() {
		orders = new TreeSet<Order>();
	}

	public static OrderRepository getInstance() {
		if (instance == null) {
			instance = new OrderRepository();
		}
		return instance;
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

	public ArrayList<Order> searchByRoom(Room room) {
		ArrayList<Order> result = new ArrayList<>();
		orders.forEach((Order order) -> {
			if (order.getRoomID().equals(room.getID())) {
				result.add(order);
			}
		});
		return result;
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

	public  ArrayList<Order> getOrders() {
		return new ArrayList<Order>(orders);
	}
	

	@Override
	public int getCount() {
		return orders.size();
	}

	@Override
	public Boolean add(AEntity entity) {
		entity.setID(IDGenerator.createOrderID());
		Boolean result = orders.add((Order) entity);
		if (result) {
			IDGenerator.addOrderID(entity.getID());
		}
		return result;
	}

	@Override
	public Boolean delete(AEntity order) {
		return orders.remove(order);
	}
	
	
}
