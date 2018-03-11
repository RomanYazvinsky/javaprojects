package com.senla.hotel.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.ClientDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    public synchronized Boolean add(Client client, boolean addId) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Boolean result = clientDao.add(client);
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public Client getClientByID(Integer id) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Client result = clientDao.getById(id);
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public ArrayList<Client> sort(SortType sortType) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            ArrayList result = clientDao.getAll(sortType);
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized ArrayList<Client> getClients() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            ArrayList<Client> clients = clientDao.getAll();
            transaction.commit();
            return clients;
        } catch (QueryFailureException | DatabaseConnectException e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
    public ArrayList<Client> importAll() {
        ArrayList<Client> clients = new ArrayList<>();
        CSVModule.importAll(Client.class).forEach(arg0 -> clients.add((Client) arg0));
        return clients;
    }

    @Override
    public synchronized void updateByImport() throws QueryFailureException, DatabaseConnectException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            for (Client client : importAll()) {
                clientDao.createOrUpdate(client);
            }
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized void exportAll() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            CSVModule.exportAll(clientDao.getAll());
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }


    @Override
    public synchronized Boolean delete(Client client) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            clientDao.delete(client);
            transaction.commit();
        } catch (QueryFailureException e) {
            transaction.rollback();
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
