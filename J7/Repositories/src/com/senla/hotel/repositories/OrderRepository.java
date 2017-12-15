package com.senla.hotel.repositories;

import com.senla.hotel.entities.Order;

import utilities.IDGenerator;

public class OrderRepository extends AEntityRepository<Order> {
	private static OrderRepository instance;

	private OrderRepository() {
	}

	public static OrderRepository getInstance() {
		if (instance == null) {
			instance = new OrderRepository();
		}
		return instance;
	}


	public Boolean add(Order entity, boolean addId) {
		if (addId) {
			entity.setId(IDGenerator.createOrderID());
		}
		Boolean result = entities.add(entity);
		if (result) {
			IDGenerator.addClientID(entity.getId());
		}
		return result;
	}
}
