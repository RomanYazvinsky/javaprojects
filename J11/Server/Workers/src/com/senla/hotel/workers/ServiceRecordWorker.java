package com.senla.hotel.workers;

import com.senla.hotel.dao.ServiceRecordDao;
import com.senla.hotel.entities.ServiceRecord;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.DateCreator;

import java.lang.reflect.InvocationTargetException;

public class ServiceRecordWorker {
    private static Logger logger = LogManager.getLogger(ServiceRecordWorker.class);
    private ServiceRecordDao serviceRecordDao;

    public ServiceRecordWorker(ServiceRecordDao serviceRecordDao) {
        this.serviceRecordDao = serviceRecordDao;
    }

    public void add(ServiceRecord serviceRecord){
        try {
            serviceRecordDao.add(serviceRecord);
        } catch (IllegalAccessException e) {
            logger.log(Level.DEBUG, e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.log(Level.DEBUG, e.getMessage());
        } catch (InvocationTargetException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
    }

    public void delete (ServiceRecord serviceRecord){
        serviceRecordDao.delete(serviceRecord);
    }

}
