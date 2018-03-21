package com.senla.hotel.dao;

import com.senla.hotel.api.internal.dao.IServiceDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.DatabaseConnectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceDao extends GenericDao<Service> implements IServiceDao<Service> {
    private static Logger logger = LogManager.getLogger(ServiceDao.class);

    public ServiceDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory(), Service.class);
    }

}
