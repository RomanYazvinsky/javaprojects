package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;

import utilities.IDGenerator;

public class ServiceRepository implements IEntityRepository {
	private static Logger logger;
	private HashSet<Service> services;
	private static ServiceRepository instance;
	
	
	static {
		logger = Logger.getLogger(ServiceRepository.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	private ServiceRepository() {
		services = new HashSet<Service>();
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
			if (service.getId().equals(id)) {
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
		entity.setId(IDGenerator.createServiceID());
		Boolean result = services.add((Service) entity);
		if (result) {
			IDGenerator.addServiceID(entity.getId());
		}
		return result;
	}

	public Boolean addNoIDGenerating(Service service) {
		Boolean result = services.add(service);
		if (result) {
			IDGenerator.addClientID(service.getId());
		}
		return result;
	}

	@Override
	public Boolean delete(AEntity entity) {
		ArrayList<Service> list = getServices();
		for (int i = 0; i < list.size(); i++) {
			if (entity.getId().equals(list.get(i).getId())) {
				return services.remove(list.get(i));
			}
		}
		return false;
	}

	public void setServices(ArrayList<Service> services) throws EmptyObjectException {
		if (services == null) {
			EmptyObjectException e = new EmptyObjectException();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
		this.services = new HashSet<>(services);
	}
}
