package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.ServiceRecord;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDao extends AEntityDAO<Order> {
    private static Logger logger = LogManager.getLogger(OrderDao.class);

    public OrderDao(Connection connection) {
        super(connection);
    }

    public Order getById(int id) {
        return read(id, Order.class);
    }

    public Boolean add(Order order) {
        try {
            create(order);
            return true;
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.DEBUG, e.getMessage());
            return false;
        }
    }

    @Override
    protected Order parseResult(ResultSet resultSet) {
        Order order = new Order();
        try {
            order.setId(resultSet.getInt("id"));
            Client client = new Client();
            Room room = new Room();
            client.setId(resultSet.getInt("client_id"));
            client.setName(resultSet.getString("client_name"));
            room.setId(resultSet.getInt("room_id"));
            room.setNumber(resultSet.getInt("number"));
            room.setCapacity(resultSet.getInt("capacity"));
            room.setStar(resultSet.getInt("star"));
            room.setPricePerDay(resultSet.getInt("room_price"));
            room.setStatus(RoomStatus.valueOf(resultSet.getString("status")));
            order.setClient(client);
            order.setRoom(room);
            Date dateFrom = resultSet.getDate("order_from");
            Date dateTo = resultSet.getDate("order_to");
            order.setOrderFrom(new java.util.Date(dateFrom.getTime()));
            if (dateTo != null) {
                order.setOrderTo(new java.util.Date(dateTo.getTime()));
            }

        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }

        return order;
    }

    @Override
    protected String getTableName() {
        Table table = Order.class.getAnnotation(Table.class);
        return table.tableName();
    }

    public ArrayList<Order> getAll() {
        try {
            return getAll(Order.class);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            return null;
        }
    }

    public ArrayList<Order> getAll(SortType sortType) {
        return getAll(Order.class, sortType);
    }

    @Override
    public void delete(Order order) {
        try {
            connection.setAutoCommit(false);
            String s = "DELETE FROM " + AEntityDAO.getTableName(ServiceRecord.class) + " WHERE order_id = " + order.getId()+";";
            connection.createStatement().execute(s);
            super.delete(order);
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
