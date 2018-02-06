package com.senla.hotel.workers;

import com.senla.hotel.entities.Client;
import com.senla.hotel.repositories.ClientRepository;

import utilities.ArrayWorker;

public class ClientWorker {
	private ClientRepository clientRepository;

	public ClientWorker() {
		clientRepository = new ClientRepository();
	}

	public Boolean add(Client client) {
		return clientRepository.add(client);
	}

	public Client getClientByID(Integer clientID) {
		return clientRepository.getByID(clientID);
	}

	public Client[] getClients() {
		return ArrayWorker.castToClient(ArrayWorker.decreaseArray(clientRepository.getClients()));
	}

	public void load(String[] clientData) {
		for (String data : clientData) {
			if (data.compareTo("")!=0) {
				add(new Client(data));
			}
		}
	}

	public String[] makeWritableArray() {
		int count = clientRepository.getCount();
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = clientRepository.getClients()[i].toString();
		}
		return result;
	}

}
