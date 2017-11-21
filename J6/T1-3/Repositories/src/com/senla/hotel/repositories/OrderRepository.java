package com.senla.hotel.repositories;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;

import utilities.IDGenerator;

public class OrderRepository implements IEntityRepository{
	private HashSet<Order> orders;
	private static OrderRepository instance;

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

	public ArrayList<Order> getOrders() {
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

	@Override
	public void save(String path) throws IOException {
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;
		fileOutputStream = new FileOutputStream(path);
		objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(orders);
		objectOutputStream.flush();
		objectOutputStream.close();
	}

	@Override
	public void load(String path) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream;
		ObjectInputStream objectInputStream;
		fileInputStream = new FileInputStream(path);
		objectInputStream = new ObjectInputStream(fileInputStream);
		orders =  (HashSet<Order>) objectInputStream.readObject();
		objectInputStream.close();
	}

}
