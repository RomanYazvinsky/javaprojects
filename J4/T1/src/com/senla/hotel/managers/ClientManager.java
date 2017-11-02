package com.senla.hotel.managers;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.utilities.ArrayWorker;

public class ClientManager extends AEntityManager {
	private static Client[] clients;

	public ClientManager() {
		super();
		if (ClientManager.clients == null) {
			entities = new Client[6];
			ClientManager.clients = ArrayWorker.castToClient(entities);
		} else {
			entities = ClientManager.clients;
		}

	}

	public Client[] getClients() {
		return (Client[]) entities;
	}

	public Client getClientByID(Integer id) {
		return (Client) getByID(id);
	}

	@Override
	protected int find(AEntity entity) {
		for (int i = 0; i < ArrayWorker.getCount(entities); i++) {
			if (clients[i] != null && clients[i].equals(entity)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	protected Boolean isCorrectType(AEntity entity) {
		return (entity instanceof Client);
	}

	@Override
	protected void extendArray() {
		ClientManager.clients = (Client[]) ArrayWorker.extendEntityArray(clients);
		entities = ClientManager.clients;
	}

	@Override
	public int getCount() {
		return ArrayWorker.getCount(ClientManager.clients);
	}

}
