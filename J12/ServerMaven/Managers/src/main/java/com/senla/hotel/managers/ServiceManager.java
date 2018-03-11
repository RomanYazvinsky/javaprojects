package com.senla.hotel.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.ServiceDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager implements com.senla.hotel.api.internal.IServiceManager {
    private static Logger logger = LogManager.getLogger(ServiceManager.class);
    private ServiceDao serviceDao;

    public ServiceManager() throws DatabaseConnectException {
        try {
            serviceDao = new ServiceDao();
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public ArrayList<Service> sort(SortType sortType) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            ArrayList<Service> result = serviceDao.getAll(sortType);
            transaction.commit();
            return result;
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
    public synchronized Boolean add(Service service, boolean addId) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Boolean result = serviceDao.add(service);
            transaction.commit();
            return result;
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
    public Integer getPriceForServices(ArrayList<Service> services) {
        if (services == null || services.size() == 0) {
            return 0;
        }
        Integer price = 0;
        for (Service service : services) {
            price += service.getPrice();
        }
        return price;
    }

    @Override
    public Service getServiceByID(Integer serviceID) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Service result = serviceDao.getById(serviceID);
            transaction.commit();
            return result;
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
    public ArrayList<Service> getServices() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            ArrayList<Service> clients = serviceDao.getAll();
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
    public String[] toStringArray(ArrayList<Service> services) {
        List<String> result = new ArrayList<>();
        for (Service service : services) {
            result.add(service.toString());
        }
        return result.toArray(new String[result.size()]);
    }

    @Override
    public synchronized ArrayList<Service> importAll() {
        ArrayList<Service> clients = new ArrayList<>();
        CSVModule.importAll(Service.class).forEach(arg0 -> clients.add((Service) arg0));
        return clients;
    }


    @Override
    public synchronized void updateByImport() throws QueryFailureException, DatabaseConnectException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            for (Service service : importAll()) {
                serviceDao.createOrUpdate(service);
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
    public synchronized void exportAll() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            CSVModule.exportAll(serviceDao.getAll());
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
    public synchronized Boolean delete(Service service) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            serviceDao.delete(service);
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
