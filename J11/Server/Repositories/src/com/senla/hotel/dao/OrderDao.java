package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
import com.senla.hotel.api.internal.IOrderDao;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDao extends GenericDao<Order> implements IOrderDao<Order> {
    private static Logger logger = LogManager.getLogger(OrderDao.class);

    public OrderDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getConnection());
    }


    @Override
    public Order getById(int id) throws QueryFailureException, AnalysisException, UnexpectedValueException {
        try {
            return read(id, Order.class);
        } catch (QueryFailureException | AnalysisException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Boolean add(Order order) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            create(order);
            return true;
        } catch (QueryFailureException | AnalysisException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    protected Order parseResult(ResultSet resultSet) throws UnexpectedValueException {
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
            throw new UnexpectedValueException();
        }

        return order;
    }

    @Override
    protected String getTableName() throws AnalysisException {
        Table table = Order.class.getAnnotation(Table.class);
        if (table == null) {
            throw new AnalysisException();
        }
        return table.tableName();
    }

    @Override
    public ArrayList<Order> getAll() throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(Order.class);

        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Order> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(Order.class, sortType);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

}
