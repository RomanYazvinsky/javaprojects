package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
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
        super(DBConnector.getInstance().getConnection());
    }

    @Override
    public Client getById(int id) throws QueryFailureException, AnalysisException, UnexpectedValueException {
        try {
            return read(id, Client.class);
        } catch (QueryFailureException | AnalysisException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Boolean add(Client client) throws QueryFailureException, AnalysisException, UnexpectedValueException {
        try {
            create(client);
            return true;
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    protected Client parseResult(ResultSet resultSet) throws UnexpectedValueException {
        Client client = new Client();
        try {
            client.setId(resultSet.getInt("id"));
            client.setName(resultSet.getString("client_name"));
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new UnexpectedValueException();
        }

        return client;
    }

    @Override
    protected String getTableName() throws AnalysisException {
        Table table = Client.class.getAnnotation(Table.class);
        if (table == null) {
            throw new AnalysisException();
        }
        return table.tableName();
    }

    @Override
    public ArrayList<Client> getAll() throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(Client.class);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Client> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(Client.class, sortType);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


}
