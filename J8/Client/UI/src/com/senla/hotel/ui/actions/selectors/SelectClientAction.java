package com.senla.hotel.ui.actions.selectors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

import utilities.Input;
import utilities.Printer;

public class SelectClientAction implements IAction {
	private static Client client;
	private static Logger logger;
	static {
		logger = Logger.getLogger(SelectClientAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Message request = new Message("getClientByID", new Object[] { Integer.parseInt(Input.userInput()) });
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			client = (Client) response.getData()[0];

			if (client == null) {
				throw new ActionForceStopException();
			}
			Printer.printClient(client);
		} catch (NumberFormatException | IndexOutOfBoundsException | IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();

		}
	}

	public static Client getClient() {
		return client;
	}

}
