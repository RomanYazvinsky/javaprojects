package com.senla.hotel.managers;

import com.senla.hotel.api.internal.IRoomManager;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RoomManager implements IRoomManager {
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
    public synchronized Boolean add(Room room, boolean addId) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Boolean result = roomDao.add(room);
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }


    @Override
    public ArrayList<Room> getRooms() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            ArrayList<Room> rooms = roomDao.getAll();
            transaction.commit();
            return rooms;
        } catch (QueryFailureException | DatabaseConnectException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }


    @Override
    public Room getRoomByID(Integer roomID) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Room result = roomDao.getById(roomID);
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public ArrayList<Room> sort(SortType sortType) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            ArrayList result = roomDao.getAll(sortType);
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
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
    public synchronized void updateByImport() throws QueryFailureException, DatabaseConnectException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            for (Room room : importAll()) {
                roomDao.createOrUpdate(room);
            }
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
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
    public synchronized void exportAll() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            CSVModule.exportAll(roomDao.getAll());
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized Boolean delete(Room room) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            roomDao.delete(room);
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
