package com.senla.hotel.workers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.senla.hotel.entities.Service;
import com.senla.hotel.repositories.ServiceRepository;

public class ServiceWorker {
	private ServiceRepository serviceRepository;

	public ServiceWorker() {
		serviceRepository = ServiceRepository.getInstance();
	}

	public ArrayList<Service> sort(ArrayList<Service> services, Comparator<Service> comparator) {
		Collections.sort(services, comparator);
		return services;
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

	public ArrayList<Service> getServices() {
		return serviceRepository.getServices();
	}

	public String[] toStringArray(ArrayList<Service> services) {
		List<String> result = new ArrayList<>();
		for (Service service : services) {
			result.add(service.toString());
		}
		return result.toArray(new String[result.size()]);
	}
}
