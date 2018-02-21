package com.senla.hotel.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.ServiceDao;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.*;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.senla.hotel.dao.com.senla.hotel.dao.connector.DBConnector;

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
    public ArrayList<Service> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException {
        try {
            return serviceDao.getAll(sortType);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public synchronized Boolean add(Service service, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            serviceDao.add(service);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
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
    public Service getServiceByID(Integer serviceID) throws AnalysisException, QueryFailureException, UnexpectedValueException {
        if (serviceID == null) {
            return null;
        }
        try {
            return serviceDao.read(serviceID, Service.class);
        } catch (QueryFailureException | AnalysisException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Service> getServices() throws QueryFailureException, UnexpectedValueException {
        try {
            return serviceDao.getAll();
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
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
    public synchronized ArrayList<Service> importAll() throws EmptyObjectException {
        ArrayList<Service> clients = new ArrayList<>();
        CSVModule.importAll(Service.class).forEach(arg0 -> clients.add((Service) arg0));
        return clients;
    }


    @Override
    public synchronized void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException {
        DBConnector dbConnector = DBConnector.getInstance();
        try {
            dbConnector.startTransaction();
            for (Service service : importAll()) {
                serviceDao.createOrUpdate(service);
            }
            dbConnector.finishTransaction();
        } catch (EmptyObjectException | QueryFailureException | AnalysisException | UnexpectedValueException | DatabaseConnectException e) {
            dbConnector.finishTransaction(true);
            logger.log(Level.DEBUG, e);
            throw e;
        }

    }

    @Override
    public synchronized void exportAll() throws QueryFailureException, UnexpectedValueException {
        try {
            CSVModule.exportAll(getServices());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public synchronized Boolean delete(Service service) throws QueryFailureException, AnalysisException {
        try {
            serviceDao.delete(service);
        } catch (AnalysisException | QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
