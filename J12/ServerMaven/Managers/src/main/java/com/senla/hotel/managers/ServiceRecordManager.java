package com.senla.hotel.managers;

import com.senla.hotel.dao.ServiceRecordDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.ServiceRecord;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

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


    public ArrayList<ServiceRecord> getServiceRecords() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            ArrayList<ServiceRecord> clients = serviceRecordDao.getAll();
            transaction.commit();
            return clients;
        } catch (QueryFailureException | DatabaseConnectException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public void add(ServiceRecord serviceRecord) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            serviceRecordDao.add(serviceRecord);
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public void delete(ServiceRecord serviceRecord) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            serviceRecordDao.delete(serviceRecord);
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized void updateByImport() throws QueryFailureException, DatabaseConnectException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            for (ServiceRecord serviceRecord : importAll()) {
                serviceRecordDao.createOrUpdate(serviceRecord);
            }
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
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
    public synchronized void exportAll() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            CSVModule.exportAll(serviceRecordDao.getAll());
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }


}
