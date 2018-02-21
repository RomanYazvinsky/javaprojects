package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
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
        super(DBConnector.getInstance().getConnection());
    }

    @Override
    public Room getById(int id) throws QueryFailureException, AnalysisException, UnexpectedValueException {
        try {
            return read(id, Room.class);
        } catch (QueryFailureException | AnalysisException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Boolean add(Room room) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            create(room);
            return true;
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    protected Room parseResult(ResultSet resultSet) throws UnexpectedValueException {
        Room room = new Room();
        try {
            room.setId(resultSet.getInt("id"));
            room.setNumber(resultSet.getInt("number"));
            room.setCapacity(resultSet.getInt("capacity"));
            room.setPricePerDay(resultSet.getInt("room_price"));
            room.setStar(resultSet.getInt("star"));
            room.setStatus(RoomStatus.valueOf(resultSet.getString("status")));

        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new UnexpectedValueException();
        }

        return room;
    }

    @Override
    protected String getTableName() throws AnalysisException {
        Table table = Room.class.getAnnotation(Table.class);
        if (table == null) {
            throw new AnalysisException();
        }
        return table.tableName();
    }

    @Override
    public ArrayList<Room> getAll() throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(Room.class);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Room> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(Room.class, sortType);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


}
