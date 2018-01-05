package com.senla.hotel.ui.actions.printers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.io.ServiceImportAction;

import utilities.Printer;

public class PrintServicesAction implements IAction {
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
			Message request = new Message("getServices");
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			ArrayList<Service> entities = (ArrayList<Service>)  response.getData()[0];
			if (entities.size() == 0) {
				logger.log(Level.SEVERE, new EmptyObjectException().getMessage());
				throw new ActionForceStopException();
			}
			for (Service service : entities) {
				Printer.printService(service);
			}
		} catch (ClassNotFoundException | IOException e) {

		}
	}

}
