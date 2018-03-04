package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;

import java.util.ArrayList;

public interface IRoomDao<T> extends IGenericDao<T> {
    Room getById(int id) throws QueryFailureException, AnalysisException, UnexpectedValueException;

    Boolean add(Room room) throws QueryFailureException, UnexpectedValueException, AnalysisException;

    ArrayList<Room> getAll() throws QueryFailureException, UnexpectedValueException;

    ArrayList<Room> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException;
}
