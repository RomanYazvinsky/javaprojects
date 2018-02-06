package com.senla.hotel.repositories;

import com.senla.hotel.entities.Client;

import utilities.IDGenerator;

public class ClientRepository extends AEntityRepository<Client> {
	private static ClientRepository instance;

	private ClientRepository() {
	}

	public static ClientRepository getInstance() {
		if (instance == null) {
			instance = new ClientRepository();
		}
		return instance;
	}

	
	public Boolean add(Client entity, boolean addId) {
		if (addId) {
			entity.setId(IDGenerator.createClientID());
		}
		Boolean result = entities.add(entity);
		if (result) {
			IDGenerator.addClientID(entity.getId());
		}
		return result;
	}
	
}
