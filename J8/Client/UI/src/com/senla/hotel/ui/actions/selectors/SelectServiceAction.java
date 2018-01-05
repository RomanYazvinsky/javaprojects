package com.senla.hotel.ui.actions.selectors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

import utilities.Input;
import utilities.Printer;

public class SelectServiceAction implements IAction {
	private static Service service;
	private static Logger logger;
	static {
		logger = Logger.getLogger(SelectServiceAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Message request = new Message("getServiceByID", new Object[] { Integer.parseInt(Input.userInput()) });
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			service = (Service)  response.getData()[0];
			Printer.printService(service);
			
		} catch (NumberFormatException | IndexOutOfBoundsException | ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

	public static Service getService() {
		return service;
	}

}
