package com.senla.hotel.ui.actions.sortprinters;

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
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;

import utilities.Printer;

public class SortServiceByNameAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(SortServiceByNameAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Message request = new Message(PublicAPI.GET_SERVICES);
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			ArrayList<Service> services = (ArrayList<Service>) response.getData()[0];
			if (services.size() == 0) {
				logger.log(Level.SEVERE, new EmptyObjectException().getMessage());
				throw new ActionForceStopException();
			}
			request = new Message(PublicAPI.SORT_SERVICES_BY_NAME, new Object[] { services });
			writer.writeObject(request);
			reader.readObject();
			Printer.printEntities(services);
		} catch (ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}
}
