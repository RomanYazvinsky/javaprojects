package com.senla.hotel.dao;

import com.senla.hotel.api.internal.dao.IServiceRecordDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.ServiceRecord;
import com.senla.hotel.exceptions.DatabaseConnectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceRecordDao extends GenericDao<ServiceRecord> implements IServiceRecordDao<ServiceRecord> {
    private static Logger logger = LogManager.getLogger(ServiceRecordDao.class);

    public ServiceRecordDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory(), ServiceRecord.class);
    }

}
