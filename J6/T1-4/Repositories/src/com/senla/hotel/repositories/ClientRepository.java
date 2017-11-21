package com.senla.hotel.repositories;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;

import utilities.CSVWorker;
import utilities.IDGenerator;

public class ClientRepository implements IEntityRepository {
	private HashSet<Client> clients;
	private static ClientRepository instance;

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
		entity.setID(IDGenerator.createClientID());
		Client client = (Client) entity;
		Boolean result = clients.add(client);
		if (result) {
			IDGenerator.addClientID(entity.getID());
		}
		return result;

	}

	public Boolean addNoIDGenerating(Client client) {
		Boolean result = clients.add(client);
		if (result) {
			IDGenerator.addClientID(client.getID());
		}
		return result;
	}

	public ArrayList<Client> getClients() {
		return new ArrayList<Client>(clients);
	}

	public Boolean delete(AEntity client) {
		ArrayList<Client> list = getClients();
		for (int i = 0; i< list.size(); i++) {
			if (client.getID().equals(list.get(i).getID())) {
				return clients.remove(list.get(i));			
			}
		}
		return false;
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

	public void export(Client client) {
		CSVWorker.exportClient(client);
	}

	@Override
	public void save(String path) throws IOException {
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;
		fileOutputStream = new FileOutputStream(path);
		objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(clients);
		objectOutputStream.flush();
		objectOutputStream.close();
	}

	@Override
	public void load(String path) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream;
		ObjectInputStream objectInputStream;
		fileInputStream = new FileInputStream(path);
		objectInputStream = new ObjectInputStream(fileInputStream);
		clients = (HashSet<Client>) objectInputStream.readObject();
		objectInputStream.close();
	}

}
