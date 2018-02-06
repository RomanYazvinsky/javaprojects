package com.senla.hotel.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.EmptyObjectException;

public interface IClientWorker {

	Boolean add(Client client, boolean addId);

	Client getClientByID(Integer clientID);

	ArrayList<Client> sort(ArrayList<Client> clients, Comparator<Client> comparator);

	ArrayList<Client> getClients();

	String[] toStringArray(ArrayList<Client> clients);

	void load(String path) throws ClassNotFoundException, IOException, EmptyObjectException;

	void save(String path) throws IOException;

	ArrayList<Client> importAll() throws EmptyObjectException;

	void exportAll();

	Boolean delete(Client client);

}