package com.senla.hotel.api.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;

public interface IServiceWorker {

	ArrayList<Service> sort(ArrayList<Service> services, Comparator<Service> comparator);

	Boolean add(Service service, boolean addId);

	Integer getPriceForServices(ArrayList<Service> services);

	Service getServiceByID(Integer serviceID);

	ArrayList<Service> getServices();

	String[] toStringArray(ArrayList<Service> services);

	ArrayList<Service> importAll() throws EmptyObjectException;

	void exportAll();

	Boolean delete(Service service);

}