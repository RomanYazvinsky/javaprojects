package com.senla.hotel.workers;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.comparators.service.ServiceNameComparator;
import com.senla.hotel.entities.Service;
import com.senla.hotel.repositories.ServiceRepository;


public class ServiceWorker {
	private ServiceRepository serviceRepository;

	public ServiceWorker() {
		serviceRepository = new ServiceRepository();
	}

	private void sortByName() {
		serviceRepository.sort(new ServiceNameComparator());
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
		return getServices();
	}

	public Service[] getServices() {
		return (Service[]) serviceRepository.getServices().toArray(new Service[serviceRepository.getCount()]);
	}

	public String[] makeWriteableArray() {
		List<String> result = new ArrayList<>();
		for (Service service : getServices()) {
			result.add(service.toString());
		}		
		return result.toArray(new String[result.size()]);
	}
}
