package com.senla.hotel.api.internal.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public interface IClientManager {
    Boolean add(Client client, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException, DatabaseConnectException;

    Client getClientByID(Integer id) throws QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    List<Client> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    List<Client> getClients() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    String[] toStringArray(ArrayList<Client> clients);

    ArrayList<Client> importAll() throws EmptyObjectException;

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Boolean delete(Client client) throws QueryFailureException, AnalysisException, DatabaseConnectException;
}
