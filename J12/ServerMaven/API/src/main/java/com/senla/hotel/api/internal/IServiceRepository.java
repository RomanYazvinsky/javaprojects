package com.senla.hotel.api.internal;

import java.util.ArrayList;

import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;

public interface IServiceRepository {

	boolean checkServices(ArrayList<Service> services);
	ArrayList<Service> get();

	Boolean add(Service entity, boolean addId);

	Boolean delete(Service entity);

	Service getByID(Integer id);

	int getCount();

	void set(ArrayList<Service> entities) throws EmptyObjectException;
}