package com.senla.hotel.api.internal.managers;

import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;

public interface IClientManager extends IGenericManager<Client> {

    String[] toStringArray(ArrayList<Client> clients);

    ArrayList<Client> importAll() throws EmptyObjectException;

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;
}
