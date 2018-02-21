package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;

public interface IRoomManager {
    Boolean add(Room room, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException;

    ArrayList<Room> getRooms() throws QueryFailureException, UnexpectedValueException;

    Room getRoomByID(Integer roomID) throws AnalysisException, QueryFailureException, UnexpectedValueException;

    ArrayList<Room> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException;

    String[] toStringArray(ArrayList<Room> rooms);

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Room> importAll() throws EmptyObjectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException;

    Boolean delete(Room room) throws QueryFailureException, AnalysisException;
}
