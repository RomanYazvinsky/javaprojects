package com.senla.hotel.managers;

import com.senla.hotel.dao.ServiceRecordDao;
import com.senla.hotel.entities.ServiceRecord;
import com.senla.hotel.exceptions.*;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.senla.hotel.dao.com.senla.hotel.dao.connector.DBConnector;

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
    public void add(ServiceRecord serviceRecord) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            serviceRecordDao.add(serviceRecord);
        } catch (QueryFailureException | AnalysisException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public void delete(ServiceRecord serviceRecord) throws QueryFailureException, AnalysisException {
        try {
            serviceRecordDao.delete(serviceRecord);
        } catch (AnalysisException | QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException {
        DBConnector dbConnector = DBConnector.getInstance();
        try {
            dbConnector.startTransaction();
            for (ServiceRecord serviceRecord : importAll()) {
                serviceRecordDao.createOrUpdate(serviceRecord);
            }
            dbConnector.finishTransaction();
        } catch (EmptyObjectException | QueryFailureException | AnalysisException | UnexpectedValueException | DatabaseConnectException e) {
            dbConnector.finishTransaction(true);
            logger.log(Level.DEBUG, e);
            throw e;
        }

    }
    @Override
    public synchronized ArrayList<ServiceRecord> importAll() throws EmptyObjectException {
        ArrayList<ServiceRecord> rooms = new ArrayList<>();
        CSVModule.importAll(ServiceRecord.class).forEach(arg0 -> rooms.add((ServiceRecord) arg0));
        return rooms;
    }


    @Override
    public synchronized void exportAll() throws QueryFailureException, UnexpectedValueException {
        try {
            CSVModule.exportAll(serviceRecordDao.getAll());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }



}
