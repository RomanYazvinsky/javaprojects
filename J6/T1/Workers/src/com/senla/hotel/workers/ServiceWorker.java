package com.senla.hotel.workers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
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

	public void load(String[] serviceData) throws NumberFormatException, ArrayIndexOutOfBoundsException, IncorrectIDEcxeption {
		try {
			for (String data : serviceData) {
				if (data.compareTo("") != 0) {
					add(new Service(data));
				}
			}
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectIDEcxeption e) {
			throw e;
		}
	}

	public Integer getPriceForServices(ArrayList<Service> services) {
		if (services == null || services.size() == 0) {
			return 0;
		}
		Integer price = 0;
		for (Service service : services) {
			price += service.getPrice();
		}
		return price;
	}

	public Service getServiceByID(Integer serviceID) {
		if (serviceID == null) {
			return null;
		}
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
