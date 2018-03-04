package com.senla.hotel.api.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;

public interface IClientWorker {

	Boolean add(Client client, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException;

	Client getClientByID(Integer clientID) throws QueryFailureException, AnalysisException, UnexpectedValueException;

	ArrayList<Client> getClients() throws QueryFailureException, UnexpectedValueException;

	String[] toStringArray(ArrayList<Client> clients);

	ArrayList<Client> importAll() throws EmptyObjectException;

	void exportAll() throws QueryFailureException, UnexpectedValueException;

	Boolean delete(Client client) throws QueryFailureException, AnalysisException;

}