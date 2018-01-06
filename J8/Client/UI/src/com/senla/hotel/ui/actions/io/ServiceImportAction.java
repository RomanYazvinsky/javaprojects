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
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

import utilities.Input;
import utilities.Printer;

public class ServiceImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(ServiceImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {

		try {
			Message request = new Message(PublicAPI.IMPORT_SERVICES, null);
			writer.writeObject(request);
			Message response = (Message) reader.readObject();
			ArrayList<Service> services = (ArrayList<Service>) response.getData()[0];
			Integer i = 1;
			for (Service service : services) {
				Printer.println(i.toString() + ") " + service.toString());
				i++;
			}
			i = Integer.parseInt(Input.userInput()) - 1;
			Service service = services.get(i);

			request = new Message(PublicAPI.DELETE_SERVICE, new Object[] { service });
			writer.writeObject(request);
			reader.readObject();
			request = new Message(PublicAPI.ADD_SERVICE_WITH_ID, new Object[] { service });
			writer.writeObject(request);
			reader.readObject();

		} catch (ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}

	}

}
