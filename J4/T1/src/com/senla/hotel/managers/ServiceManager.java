package com.senla.hotel.managers;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Service;
import com.senla.hotel.utilities.ArrayWorker;

public class ServiceManager extends AEntityManager {
	private static Service[] services;

	public ServiceManager() {
		if (ServiceManager.services == null) {
			entities= new Service[6];
			ServiceManager.services = (Service[]) entities;
		} else {
			entities = ServiceManager.services;
		}
	}

	public Service[] getServices() {
		return services;
	}

	public Service getServiceByID(Integer id) {
		return (Service) getByID(id);
	}

	@Override
	protected int find(AEntity entity) {
		for (int i = 0; i < ArrayWorker.getCount(services); i++) {
			if (services[i].equals(entity)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	protected Boolean isCorrectType(AEntity entity) {
		return entity instanceof Service;
	}

	@Override
	protected void extendArray() {
		services = (Service[]) ArrayWorker.extendEntityArray(services);
		entities = services;
	}

	@Override
	public int getCount() {
		return ArrayWorker.getCount(ServiceManager.services);
	}
}
