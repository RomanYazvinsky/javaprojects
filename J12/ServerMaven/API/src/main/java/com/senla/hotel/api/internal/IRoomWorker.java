package com.senla.hotel.api.internal;

import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;

import java.util.ArrayList;
import java.util.Comparator;

public interface IRoomWorker {

    Boolean add(Room room, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException;

    ArrayList<Room> getRooms() throws QueryFailureException;

    Room getRoomByID(Integer roomID) throws AnalysisException, QueryFailureException;

    String[] toStringArray(ArrayList<Room> rooms);

    ArrayList<Room> importAll() throws EmptyObjectException;

    void exportAll() throws QueryFailureException;

    Boolean delete(Room room) throws QueryFailureException, AnalysisException;

}