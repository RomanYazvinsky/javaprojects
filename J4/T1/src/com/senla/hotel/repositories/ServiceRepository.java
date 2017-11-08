package com.senla.hotel.repositories;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Service;

import utilities.ArrayWorker;
import utilities.IDGenerator;

public class ServiceRepository implements IEntityRepository {
	private static Service[] services;

	public ServiceRepository() {
		services = new Service[6];
	}

	public Service[] getServices() {
		return services;
	}

	@Override
	public Service getByID(Integer id) {
		for (Service service : services) {
			if (service != null) {
				if (service.getID().equals(id)) {
					return service;
				}
			}
		}
		return null;
	}

	public int getIndex(AEntity entity) {
		for (int i = 0; i < ArrayWorker.getCount(services); i++) {
			if (services[i].equals(entity)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getCount() {
		return ArrayWorker.getCount(ServiceRepository.services);
	}

	@Override
	public Boolean add(AEntity entity) {
		int orderCount = ArrayWorker.getCount(services);
		if (getIndex(entity) >= 0 && orderCount > 0) {
			return false;
		}
		if (orderCount == services.length) {
			services = (Service[]) ArrayWorker.extendEntityArray(services);
		}
		services[orderCount] = (Service) entity;
		IDGenerator.addServiceID(entity.getID());
		return true;
	}

}
