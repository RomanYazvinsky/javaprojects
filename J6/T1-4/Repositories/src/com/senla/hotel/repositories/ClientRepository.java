package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.EmptyObjectException;

import utilities.CSVWorker;
import utilities.IDGenerator;

public class ClientRepository implements IEntityRepository {
	private static Logger logger;
	private HashSet<Client> clients;
	private static ClientRepository instance;
	
	static {
		logger = Logger.getLogger(ClientRepository.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	private ClientRepository() {
		clients = new HashSet<Client>();
	}

	public static ClientRepository getInstance() {
		if (instance == null) {
			instance = new ClientRepository();
		}
		return instance;
	}

	@Override
	public Boolean add(AEntity entity) {
		entity.setId(IDGenerator.createClientID());
		Client client = (Client) entity;
		Boolean result = clients.add(client);
		if (result) {
			IDGenerator.addClientID(entity.getId());
		}
		return result;

	}

	public Boolean addNoIDGenerating(Client client) {
		Boolean result = clients.add(client);
		if (result) {
			IDGenerator.addClientID(client.getId());
		}
		return result;
	}

	public ArrayList<Client> getClients() {
		return new ArrayList<Client>(clients);
	}

	public Boolean delete(AEntity client) {
		ArrayList<Client> list = getClients();
		for (int i = 0; i< list.size(); i++) {
			if (client.getId().equals(list.get(i).getId())) {
				return clients.remove(list.get(i));			
			}
		}
		return false;
	}

	@Override
	public Client getByID(Integer id) {
		for (Client client : clients) {
			if (client.getId().equals(id)) {
				return client;
			}
		}
		return null;
	}

	@Override
	public int getCount() {
		return clients.size();
	}

	public void export(Client client) {
		CSVWorker.exportClient(client);
	}

	
	
	public void setClients(ArrayList<Client> clients) throws EmptyObjectException {
		if (clients == null) {
			logger.log(Level.SEVERE, "setClients");
			throw new EmptyObjectException();
		}
		this.clients = new HashSet<>(clients);
	}

}
