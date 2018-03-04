package com.senla.hotel.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.ClientDao;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ClientManager implements com.senla.hotel.api.internal.IClientManager {
    private static Logger logger = LogManager.getLogger(ClientManager.class);
    private ClientDao clientDao;

    public ClientManager() throws DatabaseConnectException {
        try {
            clientDao = new ClientDao();
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized Boolean add(Client client, boolean addId) throws QueryFailureException {
        try {
            return clientDao.add(client);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public Client getClientByID(Integer id) throws QueryFailureException, UnexpectedValueException {

        try {
            if (id == null) {
                throw new UnexpectedValueException();
            }
            return clientDao.getById(id);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public ArrayList<Client> sort(SortType sortType) throws QueryFailureException {
        try {
            return clientDao.getAll(sortType);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized ArrayList<Client> getClients() throws QueryFailureException {
        try {
            return clientDao.getAll();
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public String[] toStringArray(ArrayList<Client> clients) {
        ArrayList<String> result = new ArrayList<>();
        clients.forEach((Client client) -> {
            result.add(client.toString());
        });
        return result.toArray(new String[result.size()]);
    }

    @Override
    public ArrayList<Client> importAll()  {
        ArrayList<Client> clients = new ArrayList<>();
        CSVModule.importAll(Client.class).forEach(arg0 -> clients.add((Client) arg0));
        return clients;
    }

    @Override
    public synchronized void updateByImport() throws QueryFailureException {
        try {
            clientDao.batchCreateOrUpdate(importAll());
        } catch ( QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }

    }

    @Override
    public synchronized void exportAll() throws QueryFailureException {
        try {
            CSVModule.exportAll(getClients());
        } catch (QueryFailureException  e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


    @Override
    public synchronized Boolean delete(Client client) throws QueryFailureException {
        try {
            clientDao.delete(client);
        } catch ( QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
