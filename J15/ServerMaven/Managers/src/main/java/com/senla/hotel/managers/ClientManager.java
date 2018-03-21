package com.senla.hotel.managers;

import com.senla.hotel.api.internal.managers.IClientManager;
import com.senla.hotel.dao.ClientDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class ClientManager extends GenericManager<Client> implements IClientManager {
    private static Logger logger = LogManager.getLogger(ClientManager.class);

    public ClientManager() throws DatabaseConnectException {
        super(new ClientDao());
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
                dao.createOrUpdate(session, client);
            }
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
            CSVModule.exportAll(dao.getAll(session));
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

}
