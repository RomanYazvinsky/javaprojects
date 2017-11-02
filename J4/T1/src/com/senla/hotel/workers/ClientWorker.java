package com.senla.hotel.workers;

import com.senla.hotel.entities.Client;
import com.senla.hotel.managers.ClientManager;
import com.senla.hotel.utilities.ArrayWorker;

public class ClientWorker {
	private ClientManager clientManager;

	public ClientWorker() {
		clientManager = new ClientManager();
	}

	public Boolean add(Client client) {
		return clientManager.add(client);
	}

	public Client getClientByID(Integer clientID) {
		return clientManager.getClientByID(clientID);
	}

	public Client[] getClients() {
		return ArrayWorker.castToClient(ArrayWorker.decreaseArray(clientManager.getClients()));
	}

	public void load(String[] clientData) {
		for (String data : clientData) {
			if (data.compareTo("")!=0) {
				add(new Client(true, data));
			}
		}
	}

	public String[] makeWritableArray() {
		int count = clientManager.getCount();
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = clientManager.getClients()[i].toString();
		}
		return result;
	}

}
