package com.senla.hotel.dao;

import com.senla.hotel.api.internal.dao.IClientDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.DatabaseConnectException;

public class ClientDao extends GenericDao<Client> implements IClientDao<Client> {
    public ClientDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory(), Client.class);
    }
}
