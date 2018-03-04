package com.senla.hotel.dao;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.ServiceRecord;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ServiceRecordDao extends GenericDao<ServiceRecord> implements com.senla.hotel.api.internal.IServiceRecordDao<ServiceRecord> {
    private static Logger logger = LogManager.getLogger(ServiceRecordDao.class);

    public ServiceRecordDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory());
    }



    @Override
    public boolean add(ServiceRecord serviceRecord) throws QueryFailureException {
        try {
            create(serviceRecord);
            return true;
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<ServiceRecord> getAll() throws QueryFailureException {
        try {
            return getAll(ServiceRecord.class);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<ServiceRecord> getAll(SortType sortType) throws QueryFailureException {
        try {
            return new ArrayList<>(getAll(ServiceRecord.class, sortType));
        } catch (QueryFailureException  e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;

        }
    }
}
