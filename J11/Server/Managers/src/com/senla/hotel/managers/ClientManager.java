package com.senla.hotel.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.ClientDao;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.*;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.senla.hotel.dao.com.senla.hotel.dao.connector.DBConnector;

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
    public synchronized Boolean add(Client client, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            return clientDao.add(client);
        } catch (QueryFailureException | AnalysisException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public Client getClientByID(Integer id) throws QueryFailureException, AnalysisException, UnexpectedValueException {

        try {
            if (id == null) {
                throw new UnexpectedValueException();
            }
            return clientDao.getById(id);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public ArrayList<Client> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException {
        try {
            return clientDao.getAll(sortType);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public synchronized ArrayList<Client> getClients() throws QueryFailureException, UnexpectedValueException {
        try {
            return clientDao.getAll();
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
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
    public ArrayList<Client> importAll() throws EmptyObjectException {
        ArrayList<Client> clients = new ArrayList<>();
        CSVModule.importAll(Client.class).forEach(arg0 -> clients.add((Client) arg0));
        return clients;
    }

    @Override
    public synchronized void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException {
        DBConnector dbConnector = DBConnector.getInstance();
        try {
            dbConnector.startTransaction();
            for (Client client : importAll()) {
                clientDao.createOrUpdate(client);
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
            CSVModule.exportAll(getClients());
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


    @Override
    public synchronized Boolean delete(Client client) throws QueryFailureException, AnalysisException {
        try {
            clientDao.delete(client);
        } catch (AnalysisException | QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
