package com.senla.hotel.ui.actions.selectors;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.Printer;

public class SelectClientAction implements IAction {
	private static Client client;
	private static Logger logger;
	static {
		logger = Logger.getLogger(SelectClientAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	@Override
	public void execute() throws ActionForceStopException {
		try {
			client = Facade.getInstance().getClientByID(Integer.parseInt(Input.userInput()));
			if (client == null) {
				throw new ActionForceStopException();
			}
			Printer.printClient(client);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			logger.log(Level.SEVERE,  this.getClass().getName());
			throw new ActionForceStopException();

		}
	}

	public static Client getClient() {
		return client;
	}

}
