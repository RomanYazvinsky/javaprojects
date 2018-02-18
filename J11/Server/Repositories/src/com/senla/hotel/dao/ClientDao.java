package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.ServiceRecord;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ClientDao extends AEntityDAO<Client> {
    private static Logger logger = LogManager.getLogger(ClientDao.class);


    public ClientDao(Connection connection) {
        super(connection);
    }

    public Client getById(int id) {
        return read(id, Client.class);
    }

    public Boolean add(Client client) {
        try {
            create(client);
            return true;
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.DEBUG, e.getMessage());
            return false;
        }
    }

    @Override
    protected Client parseResult(ResultSet resultSet) {
        Client client = new Client();
        try {
            client.setId(resultSet.getInt("id"));
            client.setName(resultSet.getString("client_name"));
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }

        return client;
    }

    @Override
    protected String getTableName() {
        Table table = Client.class.getAnnotation(Table.class);
        return table.tableName();
    }

    public ArrayList<Client> getAll() {
        try {
            return getAll(Client.class);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            return null;
        }
    }

    public ArrayList<Client> getAll(SortType sortType) {
        return getAll(Client.class, sortType);
    }

    @Override
    public void delete(Client client) {
        try {
            connection.setAutoCommit(false);
            String s = "DELETE FROM " + AEntityDAO.getTableName(Order.class) + " WHERE client_id = " + client.getId()+";";
            connection.createStatement().execute(s);
            super.delete(client);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.log(Level.DEBUG, e.getMessage());
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.log(Level.DEBUG, e.getMessage());
            }
        }
    }

}
