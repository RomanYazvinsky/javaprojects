package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;

import java.util.ArrayList;

public interface IServiceDao<T> extends IGenericDao<T> {
    Service getById(int id) throws QueryFailureException, AnalysisException, UnexpectedValueException;

    Boolean add(Service service) throws QueryFailureException, UnexpectedValueException, AnalysisException;

    ArrayList<Service> getAll() throws QueryFailureException, UnexpectedValueException;

    ArrayList<Service> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException;
}
