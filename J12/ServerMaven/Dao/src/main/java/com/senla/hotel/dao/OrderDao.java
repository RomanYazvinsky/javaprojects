package com.senla.hotel.dao;

import com.senla.hotel.api.internal.dao.IOrderDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.DatabaseConnectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderDao extends GenericDao<Order> implements IOrderDao<Order> {
    private static Logger logger = LogManager.getLogger(OrderDao.class);

    public OrderDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory(), Order.class);
    }

}
