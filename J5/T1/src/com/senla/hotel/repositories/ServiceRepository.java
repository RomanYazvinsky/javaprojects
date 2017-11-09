package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.senla.hotel.comparators.service.ServiceIDComparator;
import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Service;

import utilities.IDGenerator;

public class ServiceRepository implements IEntityRepository {
	private static TreeSet<Service> services;

	public ServiceRepository() {
		services = new TreeSet<Service>(new ServiceIDComparator());
	}

	public TreeSet<Service> getServices() {
		return services;
	}

	public Boolean checkServices(ArrayList<Service> clientServices) {
		for (Service service : clientServices) {
			if (!services.contains(service)) {
				return false;
			}
		}
		return true;
	}
	
	public void sort(Comparator<Service> comparator) {
		List<Service> list = new ArrayList<Service>(services);
		Collections.sort(list, comparator);
		services.clear();
		services.addAll(list);
	}
	
	public void setServices(TreeSet<Service> services) {
		ServiceRepository.services = services;
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

}
