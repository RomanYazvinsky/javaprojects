package com.senla.hotel.api.internal;

import java.util.ArrayList;

import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.EmptyObjectException;

public interface IOrderRepository {
	ArrayList<Order> get();

	Boolean add(Order entity, boolean addId);

	Boolean delete(Order entity);

	Order getByID(Integer id);

	int getCount();

	void set(ArrayList<Order> entities) throws EmptyObjectException;
}