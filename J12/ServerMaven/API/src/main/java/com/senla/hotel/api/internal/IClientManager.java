package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;

public interface IClientManager {
    Boolean add(Client client, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException;

    Client getClientByID(Integer id) throws QueryFailureException, AnalysisException, UnexpectedValueException;

    ArrayList<Client> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException;

    ArrayList<Client> getClients() throws QueryFailureException, UnexpectedValueException;

    String[] toStringArray(ArrayList<Client> clients);

    ArrayList<Client> importAll() throws EmptyObjectException;

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException;

    Boolean delete(Client client) throws QueryFailureException, AnalysisException;
}
