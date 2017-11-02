package com.senla.hotel.workers;

import java.util.Arrays;

import com.senla.hotel.comparators.ServiceComparator;
import com.senla.hotel.entities.Service;
import com.senla.hotel.managers.ServiceManager;
import com.senla.hotel.utilities.ArrayWorker;

public class ServiceWorker {
	private ServiceManager serviceManager;
	private ServiceComparator serviceComparator;

	public ServiceWorker() {
		serviceManager = new ServiceManager();
		serviceComparator = new ServiceComparator();
	}

	public Boolean add(Service service) {
		return serviceManager.add(service);
	}

	public void load(String[] serviceData) {
		for (String data : serviceData) {
			if (data.compareTo("")!=0) {
				add(new Service(data));
			}
		}
	}

	public Service getServiceByID(Integer serviceID) {
		return serviceManager.getServiceByID(serviceID);
	}

	private void sortByName() {
		Arrays.sort(serviceManager.getServices(), serviceComparator.nameComparator);
	}

	public Service[] getSortedByName() {
		sortByName();
		return serviceManager.getServices();
	}

	public Service[] getServices() {
		return ArrayWorker.castToService(ArrayWorker.decreaseArray(serviceManager.getServices()));
	}

	public String[] makeWriteableArray() {
		int count = serviceManager.getCount();
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = serviceManager.getServices()[i].toString();
		}
		return result;
	}
}
