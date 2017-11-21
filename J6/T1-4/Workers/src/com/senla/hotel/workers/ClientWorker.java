package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.senla.hotel.entities.Client;
import com.senla.hotel.repositories.ClientRepository;

public class ClientWorker {
	private ClientRepository clientRepository;

	public ClientWorker() {
		clientRepository = ClientRepository.getInstance();
	}

	public Boolean add(Client client) {
		return clientRepository.add(client);
	}
	
	public Boolean addNoIDGenerating(Client client) {
		return clientRepository.addNoIDGenerating(client);
	}

	public Client getClientByID(Integer clientID) {
		if (clientID == null) {
			return null;
		}
		return clientRepository.getByID(clientID);
	}

	public ArrayList<Client> sort(ArrayList<Client> clients, Comparator<Client> comparator) {
		Collections.sort(clients, comparator);
		return clients;
	}

	public ArrayList<Client> getClients() {
		return new ArrayList<>(clientRepository.getClients());
	}

	public String[] toStringArray(ArrayList<Client> clients) {
		ArrayList<String> result = new ArrayList<>();
		clients.forEach((Client client) -> {
			result.add(client.toString());
		});
		return result.toArray(new String[result.size()]);
	}

	public void load(String path) throws ClassNotFoundException, IOException {
		clientRepository.load(path);
	}

	public void save(String path) throws IOException {
		clientRepository.save(path);
	}
	
	public void export(Client client) {
		clientRepository.export(client);
	}
	
	public Boolean delete (Client client) {
		return clientRepository.delete(client);
	}
}
