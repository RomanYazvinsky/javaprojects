package com.senla.hotel.dao.connector;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.properties.DBProperties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.DateCreator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static Logger logger = LogManager.getLogger(DateCreator.class);
    private static DBConnector instance;
    private Connection connection;

    private DBConnector() {
    }

    public static DBConnector getInstance() {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    private Connection connect() throws DatabaseConnectException {
        try {
            String host = DBProperties.getInstance(Constants.PATH_TO_DATABASE_PROPERTIES).getProperty("host");
            String username = DBProperties.getInstance(Constants.PATH_TO_DATABASE_PROPERTIES).getProperty("username");
            String password = DBProperties.getInstance(Constants.PATH_TO_DATABASE_PROPERTIES).getProperty("password");
            return DriverManager.getConnection(host, username, password);
        } catch (SQLException | IOException | EmptyObjectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new DatabaseConnectException();
        }
    }

    public Connection getConnection() throws DatabaseConnectException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = connect();
            }
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new DatabaseConnectException();
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
        return connection;
    }

    public void close() throws DatabaseConnectException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new DatabaseConnectException();
        }
    }

    public void startTransaction() throws DatabaseConnectException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new DatabaseConnectException();        }
    }

    public void finishTransaction() throws DatabaseConnectException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.log(Level.DEBUG, e.getMessage());
                throw new DatabaseConnectException();
            }
            logger.log(Level.DEBUG, e.getMessage());
            throw new DatabaseConnectException();
        }
    }
    public void finishTransaction(Boolean rollback) throws DatabaseConnectException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new DatabaseConnectException();
        }
    }


}
