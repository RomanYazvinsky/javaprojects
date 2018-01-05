package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IClientRepository;
import com.senla.hotel.api.internal.IClientWorker;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.utilities.CSVModule;

import utilities.DependencyInjector;
import utilities.Loader;
import utilities.Saver;

public class ClientWorker implements IClientWorker {
	private static Logger logger;
	private IClientRepository clientRepository;
	static {
		logger = Logger.getLogger(ClientWorker.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	public ClientWorker() {
		clientRepository = (IClientRepository) DependencyInjector.newInstance(IClientRepository.class);
	}


	
	public Boolean add(Client client, boolean addId) {
		return clientRepository.add(client, addId);
	}


	
	public Client getClientByID(Integer clientID) {
		if (clientID == null) {
			return null;
		}
		return clientRepository.getByID(clientID);
	}


	
	public ArrayList<Client> sort(ArrayList<Client> clients, Comparator<Client> comparator) {
		Collections.sort(clients, comparator);
		return clients;
	}

	
	public ArrayList<Client> getClients() {
		return new ArrayList<>(clientRepository.get());
	}

	
	public String[] toStringArray(ArrayList<Client> clients) {
		ArrayList<String> result = new ArrayList<>();
		clients.forEach((Client client) -> {
			result.add(client.toString());
		});
		return result.toArray(new String[result.size()]);
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IClientWorker#load(java.lang.String)
	 */
	
	public void load(String path) throws ClassNotFoundException, IOException, EmptyObjectException {
		try {
			clientRepository.set(Loader.loadClients(path));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IClientWorker#save(java.lang.String)
	 */
	
	public void save(String path) throws IOException {
		try {
			Saver.saveClients(path, clientRepository.get());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IClientWorker#importAll()
	 */
	
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

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IClientWorker#exportAll()
	 */
	
	public void exportAll() {
		CSVModule.exportAll(getClients());
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IClientWorker#delete(com.senla.hotel.entities.Client)
	 */
	
	public Boolean delete(Client client) {
		return clientRepository.delete(client);
	}
}
