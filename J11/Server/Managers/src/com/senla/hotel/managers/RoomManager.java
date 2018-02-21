package com.senla.hotel.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.*;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.senla.hotel.dao.com.senla.hotel.dao.connector.DBConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    public synchronized Boolean add(Room room, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            return roomDao.add(room);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }

    }


    @Override
    public ArrayList<Room> getRooms() throws QueryFailureException, UnexpectedValueException {

        try {
            return roomDao.getAll();
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


    @Override
    public Room getRoomByID(Integer roomID) throws AnalysisException, QueryFailureException, UnexpectedValueException {
        if (roomID == null) {
            return null;
        }
        try {
            return roomDao.read(roomID, Room.class);
        } catch (QueryFailureException | AnalysisException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Room> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException {

        try {
            return roomDao.getAll(sortType);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
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
    public synchronized void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException {
        DBConnector dbConnector = DBConnector.getInstance();
        try {
            dbConnector.startTransaction();
            for (Room room : importAll()) {
                roomDao.createOrUpdate(room);
            }
            dbConnector.finishTransaction();
        } catch (EmptyObjectException | QueryFailureException | AnalysisException | UnexpectedValueException | DatabaseConnectException e) {
            dbConnector.finishTransaction(true);
            logger.log(Level.DEBUG, e);
            throw e;
        }

    }

    @Override
    public synchronized ArrayList<Room> importAll() throws EmptyObjectException {
        ArrayList<Room> rooms = new ArrayList<>();
        CSVModule.importAll(Room.class).forEach(new Consumer<Object>() {

            @Override
            public void accept(Object arg0) {
                rooms.add((Room) arg0);
            }
        });
        return rooms;
    }


    @Override
    public synchronized void exportAll() throws QueryFailureException, UnexpectedValueException {
        try {
            CSVModule.exportAll(getRooms());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public synchronized Boolean delete(Room room) throws QueryFailureException, AnalysisException {
        try {
            roomDao.delete(room);
        } catch (AnalysisException | QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
