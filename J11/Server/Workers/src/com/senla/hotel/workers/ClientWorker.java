package com.senla.hotel.workers;

import com.senla.hotel.api.internal.IClientWorker;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.ClientDao;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.DBConnector;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ClientWorker implements IClientWorker {
    private static Logger logger = LogManager.getLogger(ClientWorker.class);
    private ClientDao clientDao;

    public ClientWorker() {
        clientDao = new ClientDao(DBConnector.getInstance().getConnection());
    }

    public synchronized Boolean add(Client client, boolean addId) {
        return clientDao.add(client);
    }

    public Client getClientByID(Integer id) {
        if (id == null) {
            return null;
        }
        return clientDao.getById(id);
    }

    public ArrayList<Client> sort(SortType sortType) {
        return clientDao.getAll(sortType);
    }

    public synchronized ArrayList<Client> getClients() {
        return clientDao.getAll();
    }

    public String[] toStringArray(ArrayList<Client> clients) {
        ArrayList<String> result = new ArrayList<>();
        clients.forEach((Client client) -> {
            result.add(client.toString());
        });
        return result.toArray(new String[result.size()]);
    }

    public ArrayList<Client> importAll() throws EmptyObjectException {
        ArrayList<Client> clients = new ArrayList<>();
        CSVModule.importAll(Client.class).forEach(new Consumer<Object>() {

            @Override
            public void accept(Object arg0) {
                clients.add((Client) arg0);
            }
        });
        return clients;
    }


    public synchronized void exportAll() {
        CSVModule.exportAll(getClients());
    }


    public synchronized Boolean delete(Client client) {
        clientDao.delete(client);
        return true;
    }
}
