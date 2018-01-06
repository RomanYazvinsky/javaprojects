package com.senla.hotel.ui.actions.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

import utilities.Input;
import utilities.Printer;

public class ClientImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(ClientImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Message request = new Message(PublicAPI.IMPORT_CLIENTS, null);
			writer.writeObject(request);
			Message response = (Message) reader.readObject();
			ArrayList<Client> clients = (ArrayList<Client>) response.getData()[0];
			Integer i = 1;
			for (Client client : clients) {
				Printer.println(i.toString() + ") " + client.toString());
				i++;
			}
			i = Integer.parseInt(Input.userInput()) - 1;
			Client client = clients.get(i);
			request = new Message(PublicAPI.DELETE_CLIENT, new Object[] {client});
			writer.writeObject(request);
			reader.readObject();
			request = new Message(PublicAPI.ADD_CLIENT_WITH_ID, new Object[] {client});
			writer.writeObject(request);
			reader.readObject();

		}catch( ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
		
		
	}

}
