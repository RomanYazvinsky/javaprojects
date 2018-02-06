package com.senla.hotel.workers;

import java.util.Arrays;

import com.senla.hotel.comparators.service.ServiceNameComparator;
import com.senla.hotel.entities.Service;
import com.senla.hotel.repositories.ServiceRepository;

import utilities.ArrayWorker;

public class ServiceWorker {
	private ServiceRepository serviceRepository;

	public ServiceWorker() {
		serviceRepository = new ServiceRepository();
	}

	private void sortByName() {
		Arrays.sort(serviceRepository.getServices(), new ServiceNameComparator());
	}

	public Boolean add(Service service) {
		return serviceRepository.add(service);
	}

	public void load(String[] serviceData) {
		for (String data : serviceData) {
			if (data.compareTo("") != 0) {
				add(new Service(data));
			}
		}
	}

	public Service getServiceByID(Integer serviceID) {
		return serviceRepository.getByID(serviceID);
	}

	public Service[] getSortedByName() {
		sortByName();
		return serviceRepository.getServices();
	}

	public Service[] getServices() {
		return ArrayWorker.castToService(ArrayWorker.decreaseArray(serviceRepository.getServices()));
	}

	public String[] makeWriteableArray() {
		int count = serviceRepository.getCount();
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = serviceRepository.getServices()[i].toString();
		}
		return result;
	}
}
