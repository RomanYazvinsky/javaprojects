package com.senla.hotel.repositories;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;

import utilities.ArrayWorker;
import utilities.IDGenerator;

public class ClientRepository implements IEntityRepository {
	private static Client[] clients;

	public ClientRepository() {
		clients = new Client[6];
	}

	@Override
	public Boolean add(AEntity entity) {
		int clientCount = ArrayWorker.getCount(clients);
		if (getIndex(entity) >= 0 && clientCount > 0) {
			return false;
		}
		if (clientCount == clients.length) {
			clients = (Client[]) ArrayWorker.extendEntityArray(clients);
		}
		clients[clientCount] = (Client) entity;
		IDGenerator.addClientID(entity.getID());
		return true;

	}

	public Client[] getClients() {
		return clients;
	}

	@Override
	public Client getByID(Integer id) {
		for (Client client : clients) {
			if (client != null) {
				if (client.getID().equals(id)) {
					return client;
				}
			}
		}
		return null;
	}

	@Override
	public int getIndex(AEntity entity) {
		for (int i = 0; i < ArrayWorker.getCount(clients); i++) {
			if (clients[i] != null && clients[i].equals(entity)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getCount() {
		return ArrayWorker.getCount(clients);
	}

}
