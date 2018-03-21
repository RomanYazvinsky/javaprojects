package com.senla.hotel.api.internal.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.IEntity;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;

import java.util.List;

public interface IGenericManager<T extends IEntity> {
    Boolean add(T entity, boolean addId) throws QueryFailureException, DatabaseConnectException;

    T getById(Integer id) throws QueryFailureException, DatabaseConnectException;

    List<T> sort(SortType sortType) throws QueryFailureException, DatabaseConnectException;

    List<T> getAll() throws QueryFailureException, DatabaseConnectException;

    Boolean delete(T entity) throws QueryFailureException, DatabaseConnectException;
}
