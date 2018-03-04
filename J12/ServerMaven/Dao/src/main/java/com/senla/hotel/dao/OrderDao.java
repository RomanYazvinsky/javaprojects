package com.senla.hotel.dao;

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
        super(DBConnector.getInstance().getSessionFactory());
    }


    @Override
    public Order getById(int id) throws QueryFailureException{
        try {
            return read(id, Order.class);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Boolean add(Order order) throws QueryFailureException{
        try {
            create(order);
            return true;
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Order> getAll() throws QueryFailureException {
        try {
            return getAll(Order.class);

        } catch (QueryFailureException  e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Order> getAll(SortType sortType) throws QueryFailureException {
        try {
            return (ArrayList<Order>) getAll(Order.class, sortType);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

}
