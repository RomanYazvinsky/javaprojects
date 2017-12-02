package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.EmptyObjectException;

import utilities.IDGenerator;

public class OrderRepository implements IEntityRepository {
	private static Logger logger;
	private HashSet<Order> orders;
	private static OrderRepository instance;
	
	
	static {
		logger = Logger.getLogger(OrderRepository.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	private OrderRepository() {
		orders = new HashSet<Order>();
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
			if (order.getId().equals(id)) {
				return order;
			}
		}
		return null;
	}

	public ArrayList<Order> searchByRoom(Room room) {
		ArrayList<Order> result = new ArrayList<>();
		orders.forEach((Order order) -> {
			if (order.getRoomId().equals(room.getId())) {
				result.add(order);
			}
		});
		return result;
	}

	public ArrayList<Order> searchByClient(Client client) {
		ArrayList<Order> result = new ArrayList<>();
		orders.forEach((Order order) -> {
			if (order.getClientId().equals(client.getId())) {
				result.add(order);
			}
		});
		return result;
	}

	public ArrayList<Order> getOrders() {
		return new ArrayList<Order>(orders);
	}

	@Override
	public int getCount() {
		return orders.size();
	}

	@Override
	public Boolean add(AEntity entity) {
		entity.setId(IDGenerator.createOrderID());
		Boolean result = orders.add((Order) entity);
		if (result) {
			IDGenerator.addOrderID(entity.getId());
		}
		return result;
	}

	public Boolean addNoIDGenerating(Order order) {
		Boolean result = orders.add(order);
		if (result) {
			IDGenerator.addOrderID(order.getId());
		}
		return result;
	}

	@Override
	public Boolean delete(AEntity order) {
		ArrayList<Order> list = getOrders();
		for (int i = 0; i < list.size(); i++) {
			if (order.getId().equals(list.get(i).getId())) {
				return orders.remove(list.get(i));
			}
		}
		return false;
	}
	
	public void setOrders(ArrayList<Order> orders) throws EmptyObjectException {
		if (orders == null) {
			EmptyObjectException e = new EmptyObjectException();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
		this.orders = new HashSet<>(orders);
	}

}
