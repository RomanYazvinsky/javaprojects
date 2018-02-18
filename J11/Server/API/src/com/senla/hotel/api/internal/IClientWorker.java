package com.senla.hotel.api.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.EmptyObjectException;

public interface IClientWorker {

	Boolean add(Client client, boolean addId);

	Client getClientByID(Integer clientID);

	ArrayList<Client> getClients();

	String[] toStringArray(ArrayList<Client> clients);

	ArrayList<Client> importAll() throws EmptyObjectException;

	void exportAll();

	Boolean delete(Client client);

}