package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;

import java.util.ArrayList;

public interface IClientDao<T> extends IGenericDao<T> {
    Client getById(int id) throws QueryFailureException, AnalysisException, UnexpectedValueException;

    Boolean add(Client client) throws QueryFailureException, AnalysisException, UnexpectedValueException;

    ArrayList<Client> getAll() throws QueryFailureException, UnexpectedValueException;

    ArrayList<Client> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException;
}
