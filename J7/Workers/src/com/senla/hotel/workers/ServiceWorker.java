package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.repositories.ServiceRepository;
import com.senla.hotel.utilities.CSVModule;

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

	public Boolean add(Service service, boolean addId) {
		return serviceRepository.add(service, addId);
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
		return serviceRepository.get();
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
			serviceRepository.set(Loader.loadServices(path));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public void save(String path) throws IOException {
		try {
			Saver.saveServices(path, serviceRepository.get());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}
	public ArrayList<Service> importAll() throws EmptyObjectException {
		ArrayList<Service> clients = new ArrayList<>();
		CSVModule.importAll(Service.class).forEach(new Consumer<Object>() {

			@Override
			public void accept(Object arg0) {
				clients.add((Service) arg0);
			}
		});
		return clients;
	}

	public void exportAll() {
		CSVModule.exportAll(getServices());
	}

	public Boolean delete(Service service) {
		return serviceRepository.delete(service);
	}
}
