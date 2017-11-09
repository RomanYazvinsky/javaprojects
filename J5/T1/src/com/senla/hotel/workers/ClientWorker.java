package com.senla.hotel.workers;

import java.util.ArrayList;

import com.senla.hotel.entities.Client;
import com.senla.hotel.repositories.ClientRepository;

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
		return clientRepository.getClients().toArray(new Client[clientRepository.getCount()]);
	}

	public void load(String[] clientData) {
		for (String data : clientData) {
			if (data.compareTo("") != 0) {
				add(new Client(data));
			}
		}
	}

	public String[] makeWritableArray() {
		ArrayList<String> result = new ArrayList<>();
		clientRepository.getClients().forEach((Client client) -> {
			result.add(client.toString());
		});
		return result.toArray(new String[result.size()]);
	}

}
