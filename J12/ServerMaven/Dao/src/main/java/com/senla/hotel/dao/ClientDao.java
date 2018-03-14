package com.senla.hotel.dao;

import com.senla.hotel.api.internal.dao.IClientDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.DatabaseConnectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientDao extends GenericDao<Client> implements IClientDao<Client> {
    private static Logger logger = LogManager.getLogger(ClientDao.class);


    public ClientDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory(), Client.class);
    }
}
