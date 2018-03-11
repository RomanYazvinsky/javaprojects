package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;

public interface IRoomManager {
    Boolean add(Room room, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException, DatabaseConnectException;

    ArrayList<Room> getRooms() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Room getRoomByID(Integer roomID) throws AnalysisException, QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Room> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    String[] toStringArray(ArrayList<Room> rooms);

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Room> importAll() throws EmptyObjectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Boolean delete(Room room) throws QueryFailureException, AnalysisException, DatabaseConnectException;
}
