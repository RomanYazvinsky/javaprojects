package com.senla.hotel.repositories;

import java.util.TreeSet;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.idcomparators.ClientIDComparator;

import utilities.IDGenerator;

public class ClientRepository implements IEntityRepository {
	private static TreeSet<Client> clients;

	public ClientRepository() {
		clients = new TreeSet<Client>(new ClientIDComparator());
	}

	@Override
	public Boolean add(AEntity entity) {
		Boolean result = clients.add((Client) entity);
		if (result) {
			IDGenerator.addClientID(entity.getID());
		}
		return result;

	}

	public TreeSet<Client> getClients() {
		return clients;
	}
	
	public void setClients(TreeSet<Client> clients) {
		ClientRepository.clients = clients;
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
