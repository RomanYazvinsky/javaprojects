package com.senla.hotel.ui.actions.addition;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.IncorrectNameException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.Printer;

public class AddClientAction implements IAction {
	private static Logger logger;
	
	static {
		logger = Logger.getLogger(AddClientAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}
	@Override
	public void execute() throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_CLIENT_PARAMS.toString());
		String userInput = Input.userInput();
		try {
			Facade.getInstance().addClient(new Client(userInput));
		} catch (NumberFormatException | NullPointerException | IncorrectNameException  e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
