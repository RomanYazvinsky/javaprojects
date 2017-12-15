package com.senla.hotel.ui.actions.io;

import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.ui.actions.IAction;

public class ClientImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(ClientImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	@Override
	public void execute() throws ActionForceStopException {
	/*	try {
			ArrayList<Client> clients = CSVWorker.importClients();
			Integer i = 1;
			for (Client client : clients) {
				Printer.println(i.toString() + ") " + client.toString());
				i++;
			}
			i = Integer.parseInt(Input.userInput()) - 1;
			Client client = clients.get(i);
			Facade.getInstance().deleteClient(client);
			Facade.getInstance().addClientWithID(client);
		} catch (ActionForceStopException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}*/
	}

}
