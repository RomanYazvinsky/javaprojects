package com.senla.hotel.dao.connector;

import com.senla.hotel.exceptions.DatabaseConnectException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import utilities.ui.DateCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DBConnector {
    private static Logger logger = LogManager.getLogger(DateCreator.class);
    private static DBConnector instance;
    private SessionFactory sessionFactory;

    private DBConnector() {
    }

    public static DBConnector getInstance() {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    private SessionFactory connect() throws DatabaseConnectException {
        try {
           File file = new File("hibernate.cfg.xml");
           Configuration configuration = new Configuration().configure(file);
           StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
           return configuration.buildSessionFactory(builder.build());
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new DatabaseConnectException();
        }
    }

    public SessionFactory getSessionFactory() throws DatabaseConnectException {
        try {
            if (sessionFactory == null || sessionFactory.isClosed()) {
                sessionFactory = connect();
            }
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
        return sessionFactory;
    }

    public void close() throws DatabaseConnectException {
        try {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new DatabaseConnectException();
        }
    }



}
