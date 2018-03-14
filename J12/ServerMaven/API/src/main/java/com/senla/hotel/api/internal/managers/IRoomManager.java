package com.senla.hotel.api.internal.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public interface IRoomManager {
    Boolean add(Room room, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException, DatabaseConnectException;

    List<Room> getRooms() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Room getRoomByID(Integer roomID) throws AnalysisException, QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    List<Room> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    String[] toStringArray(ArrayList<Room> rooms);

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Room> importAll() throws EmptyObjectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Boolean delete(Room room) throws QueryFailureException, AnalysisException, DatabaseConnectException;
}
