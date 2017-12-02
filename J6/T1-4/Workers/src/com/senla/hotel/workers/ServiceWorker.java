package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.repositories.ServiceRepository;

import utilities.CSVWorker;
import utilities.Loader;
import utilities.Saver;

public class ServiceWorker {
	private static Logger logger;
	private ServiceRepository serviceRepository;
	static {
		logger = Logger.getLogger(ServiceWorker.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

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

	public Boolean addNoIDGenerating(Service service) {
		return serviceRepository.addNoIDGenerating(service);
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

	public void load(String path) throws ClassNotFoundException, IOException, EmptyObjectException {
		try {
			serviceRepository.setServices(Loader.loadServices(path));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public void save(String path) throws IOException {
		try {
			Saver.saveServices(path, serviceRepository.getServices());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public void export(Service service) {
		CSVWorker.exportService(service);
	}
	
	public void exportAll() {
		CSVWorker.exportServices(serviceRepository.getServices());
	}

	public Boolean delete(Service service) {
		return serviceRepository.delete(service);
	}
}
