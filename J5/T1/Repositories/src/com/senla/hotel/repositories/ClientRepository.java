package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.TreeSet;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;

import utilities.IDGenerator;

public class ClientRepository implements IEntityRepository {
	private TreeSet<Client> clients;
	private static ClientRepository instance;

	private ClientRepository() {
		clients = new TreeSet<Client>();
	}
	
	public static ClientRepository getInstance() {
		if (instance == null) {
			instance = new ClientRepository();
		}
		return instance;
	}

	@Override
	public Boolean add(AEntity entity) {
		entity.setID(IDGenerator.createClientID());
		Boolean result = clients.add((Client) entity);
		if (result) {
			IDGenerator.addClientID(entity.getID());
		}
		return result;

	}

	public ArrayList<Client> getClients() {
		return new ArrayList<Client>(clients);
	}
	
	public Boolean delete(AEntity client) {
		return clients.remove(client);
	}

	@Override
	public Client getByID(Integer id) {
		for (Client client : clients) {
			if (client.getID().equals(id)) {
				return client;
			}
		}
		return null;
	}

	@Override
	public int getCount() {
		return clients.size();
	}

}
