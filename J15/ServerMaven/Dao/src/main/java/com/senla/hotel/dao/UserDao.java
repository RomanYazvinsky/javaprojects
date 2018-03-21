package com.senla.hotel.dao;

import com.senla.hotel.api.internal.dao.IUserDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.User;
import com.senla.hotel.exceptions.DatabaseConnectException;

public class UserDao extends GenericDao<User> implements IUserDao<User> {
    public UserDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory(), User.class);
    }
}
