package com.senla.hotel.managers;

import com.senla.hotel.dao.ServiceRecordDao;
import com.senla.hotel.entities.ServiceRecord;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ServiceRecordManager implements com.senla.hotel.api.internal.IServiceRecordManager {
    private static Logger logger = LogManager.getLogger(ServiceRecordManager.class);
    private ServiceRecordDao serviceRecordDao;

    public ServiceRecordManager() throws DatabaseConnectException {
        try {
            this.serviceRecordDao = new ServiceRecordDao();
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public void add(ServiceRecord serviceRecord) throws QueryFailureException {
        try {
            serviceRecordDao.add(serviceRecord);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public void delete(ServiceRecord serviceRecord) throws QueryFailureException {
        try {
            serviceRecordDao.delete(serviceRecord);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized void updateByImport() throws QueryFailureException {
        try {
            serviceRecordDao.batchCreateOrUpdate(importAll());
        } catch ( QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }

    }
    @Override
    public synchronized ArrayList<ServiceRecord> importAll() {
        ArrayList<ServiceRecord> rooms = new ArrayList<>();
        CSVModule.importAll(ServiceRecord.class).forEach(arg0 -> rooms.add((ServiceRecord) arg0));
        return rooms;
    }


    @Override
    public synchronized void exportAll() throws QueryFailureException {
        try {
            CSVModule.exportAll(serviceRecordDao.getAll());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }



}
