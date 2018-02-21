package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;

import java.util.ArrayList;

public interface IOrderDao<T> extends IGenericDao<T> {
    Order getById(int id) throws QueryFailureException, AnalysisException, UnexpectedValueException;

    Boolean add(Order order) throws QueryFailureException, UnexpectedValueException, AnalysisException;

    ArrayList<Order> getAll() throws QueryFailureException, UnexpectedValueException;

    ArrayList<Order> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException;
}
