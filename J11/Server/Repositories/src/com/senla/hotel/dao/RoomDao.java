package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.ServiceRecord;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDao extends AEntityDAO<Room> {
    private static Logger logger = LogManager.getLogger(RoomDao.class);

    public RoomDao(Connection connection) {
        super(connection);
    }

    public Room getById(int id) {
        return read(id, Room.class);
    }

    public Boolean add(Room room) {
        try {
            create(room);
            return true;
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.DEBUG, e.getMessage());
            return false;
        }
    }

    @Override
    protected Room parseResult(ResultSet resultSet) {
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
        }

        return room;
    }

    @Override
    protected String getTableName() {
        Table table = Room.class.getAnnotation(Table.class);
        return table.tableName();
    }

    public ArrayList<Room> getAll() {
        try {
            return getAll(Room.class);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            return null;
        }
    }

    public ArrayList<Room> getAll(SortType sortType) {
        return getAll(Room.class, sortType);
    }

    @Override
    public void delete(Room room) {
        try {
            connection.setAutoCommit(false);
            String s = "DELETE FROM " + AEntityDAO.getTableName(Order.class) + " WHERE room_id = " +  room.getId()+";";
            connection.createStatement().execute(s);
            super.delete(room);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.log(Level.DEBUG, e.getMessage());
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.log(Level.DEBUG, e.getMessage());
            }
        }
    }
}
