package com.senla.hotel.repositories;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Service;

import utilities.IDGenerator;

public class ServiceRepository implements IEntityRepository{
	private HashSet<Service> services;
	private static ServiceRepository instance;

	private ServiceRepository() {
		services = new HashSet<Service>();
	}

	public static ServiceRepository getInstance() {
		if (instance == null) {
			instance = new ServiceRepository();
		}
		return instance;
	}

	public ArrayList<Service> getServices() {

		return new ArrayList<>(services);
	}

	public Boolean checkServices(ArrayList<Service> clientServices) {
		for (Service service : clientServices) {
			if (!services.contains(service)) {
				return false;
			}
		}
		return true;
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
		entity.setID(IDGenerator.createServiceID());
		Boolean result = services.add((Service) entity);
		if (result) {
			IDGenerator.addServiceID(entity.getID());
		}
		return result;
	}

	@Override
	public Boolean delete(AEntity entity) {
		return services.remove(entity);
	}

	@Override
	public void save(String path) throws IOException {
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;
		fileOutputStream = new FileOutputStream(path);
		objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(services);
		objectOutputStream.flush();
		objectOutputStream.close();
	}

	@Override
	public void load(String path) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream;
		ObjectInputStream objectInputStream;
		fileInputStream = new FileInputStream(path);
		objectInputStream = new ObjectInputStream(fileInputStream);
		services = (HashSet<Service>) objectInputStream.readObject();
		objectInputStream.close();
	}
}
