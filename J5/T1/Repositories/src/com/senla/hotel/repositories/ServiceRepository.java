package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.TreeSet;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Service;

import utilities.IDGenerator;

public class ServiceRepository implements IEntityRepository {
	private TreeSet<Service> services;
	private static ServiceRepository instance;

	private ServiceRepository() {
		services = new TreeSet<Service>();
	}
	
	public static ServiceRepository getInstance() {
		if (instance == null) {
			instance = new ServiceRepository();
		}
		return instance;
	}

	public ArrayList<Service> getServices() {
		return new ArrayList<>(services);
	}

	public Boolean checkServices(ArrayList<Service> clientServices) {
		for (Service service : clientServices) {
			if (!services.contains(service)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Service getByID(Integer id) {
		for (Service service : services) {
			if (service.getID().equals(id)) {
				return service;
			}
		}
		return null;
	}

	@Override
	public int getCount() {
		return services.size();
	}

	@Override
	public Boolean add(AEntity entity) {
		Boolean result = services.add((Service) entity);
		if (result) {
			IDGenerator.addServiceID(entity.getID());
		}
		return result;
	}

	@Override
	public Boolean delete(AEntity entity) {
		return services.remove(entity);
	}

}
