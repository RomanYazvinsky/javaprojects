package com.senla.hotel.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RoomManager implements com.senla.hotel.api.internal.IRoomManager {
    private static Logger logger = LogManager.getLogger(RoomManager.class);
    private RoomDao roomDao;

    public RoomManager() throws DatabaseConnectException {
        try {
            roomDao = new RoomDao();
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized Boolean add(Room room, boolean addId) throws QueryFailureException{
        try {
            return roomDao.add(room);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }

    }


    @Override
    public ArrayList<Room> getRooms() throws QueryFailureException {

        try {
            return roomDao.getAll();
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }


    @Override
    public Room getRoomByID(Integer roomID) throws QueryFailureException {
        if (roomID == null) {
            return null;
        }
        try {
            return roomDao.read(roomID, Room.class);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public ArrayList<Room> sort(SortType sortType) throws QueryFailureException {

        try {
            return roomDao.getAll(sortType);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public String[] toStringArray(ArrayList<Room> rooms) {
        List<String> result = new ArrayList<>();
        for (Room room : rooms) {
            result.add(room.toString());
        }
        return result.toArray(new String[result.size()]);
    }

    @Override
    public synchronized void updateByImport() throws QueryFailureException {
        try {
            roomDao.batchCreateOrUpdate(importAll());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }

    }

    @Override
    public synchronized ArrayList<Room> importAll() {
        ArrayList<Room> rooms = new ArrayList<>();
        CSVModule.importAll(Room.class).forEach(arg0 -> rooms.add((Room) arg0));
        return rooms;
    }


    @Override
    public synchronized void exportAll() throws QueryFailureException {
        try {
            CSVModule.exportAll(getRooms());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized Boolean delete(Room room) throws QueryFailureException {
        try {
            roomDao.delete(room);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
