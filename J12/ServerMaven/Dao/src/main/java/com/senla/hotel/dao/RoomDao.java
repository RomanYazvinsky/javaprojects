package com.senla.hotel.dao;

import com.senla.hotel.api.internal.IRoomDao;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDao extends GenericDao<Room> implements IRoomDao<Room> {
    private static Logger logger = LogManager.getLogger(RoomDao.class);

    public RoomDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory());
    }

    @Override
    public Room getById(int id) throws QueryFailureException  {
        try {
            return read(id, Room.class);
        } catch (QueryFailureException  e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Boolean add(Room room) throws QueryFailureException {
        try {
            create(room);
            return true;
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


    @Override
    public ArrayList<Room> getAll() throws QueryFailureException {
        try {
            return getAll(Room.class);
        } catch (QueryFailureException  e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Room> getAll(SortType sortType) throws QueryFailureException {
        try {
            return new ArrayList<>(getAll(Room.class, sortType));
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


}
