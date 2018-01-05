package com.senla.hotel.repositories;

import java.util.ArrayList;

import com.senla.hotel.api.internal.IServiceRepository;
import com.senla.hotel.entities.Service;

import utilities.IDGenerator;

public class ServiceRepository extends AEntityRepository<Service> implements IServiceRepository {
	private static ServiceRepository instance;

	private ServiceRepository() {
	}

	public static ServiceRepository getInstance() {
		if (instance == null) {
			instance = new ServiceRepository();
		}
		return instance;
	}
	
	public synchronized Boolean add(Service entity, boolean addId) {
		if (addId) {
			entity.setId(IDGenerator.createClientID());
		}
		Boolean result = entities.add(entity);
		if (result) {
			IDGenerator.addClientID(entity.getId());
		}
		return result;
	}

	@Override
	public synchronized boolean checkServices(ArrayList<Service> services) {
		for (Service service : services) {
			if (!entities.contains(service)) {
				return false;
			}
		}
		return true;
	}
}
