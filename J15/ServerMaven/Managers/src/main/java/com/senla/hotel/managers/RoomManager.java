package com.senla.hotel.managers;

import com.senla.hotel.api.internal.managers.IRoomManager;
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

public class RoomManager extends GenericManager<Room> implements IRoomManager {
    private static Logger logger = LogManager.getLogger(RoomManager.class);

    public RoomManager() throws DatabaseConnectException {
        super(new RoomDao());
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
                dao.createOrUpdate(session, room);
            }
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
            CSVModule.exportAll(dao.getAll(session));
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

}
