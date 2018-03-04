package com.senla.hotel.dao;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ServiceDao extends GenericDao<Service> implements com.senla.hotel.api.internal.IServiceDao<Service> {
    private static Logger logger = LogManager.getLogger(ServiceDao.class);

    public ServiceDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory());
    }

    @Override
    public Service getById(int id) throws QueryFailureException {
        try {
            return read(id, Service.class);
        } catch (QueryFailureException  e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Boolean add(Service service) throws QueryFailureException{
        try {
            create(service);
            return true;
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Service> getAll() throws QueryFailureException {
        try {
            return getAll(Service.class);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Service> getAll(SortType sortType) throws QueryFailureException {
        try {
            return new ArrayList<>(getAll(Service.class, sortType));
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;

        }
    }


}
