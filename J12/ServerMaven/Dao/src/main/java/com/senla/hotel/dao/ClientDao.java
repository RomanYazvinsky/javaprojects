package com.senla.hotel.dao;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ClientDao extends GenericDao<Client> implements com.senla.hotel.api.internal.IClientDao<Client> {
    private static Logger logger = LogManager.getLogger(ClientDao.class);


    public ClientDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory());
    }

    @Override
    public Client getById(int id) throws QueryFailureException{
        try {
            return read(id, Client.class);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Boolean add(Client client) throws QueryFailureException {
        try {
            create(client);
            return true;
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Client> getAll() throws QueryFailureException {
        try {
            return getAll(Client.class);
        } catch (QueryFailureException  e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Client> getAll(SortType sortType) throws QueryFailureException {
        try {
            return (ArrayList<Client>) getAll(Client.class, sortType);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


}
