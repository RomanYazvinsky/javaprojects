package com.senla.hotel.repositories;

import com.senla.hotel.api.internal.IClientRepository;
import com.senla.hotel.entities.Client;

import utilities.IDGenerator;

public class ClientRepository extends AEntityRepository<Client> implements IClientRepository {
	private static IClientRepository instance;

	private ClientRepository() {
	}

	public static IClientRepository getInstance() {
		if (instance == null) {
			instance = new ClientRepository();
		}
		return instance;
	}

	@Override
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
