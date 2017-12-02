package com.senla.hotel.ui.actions.addition;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.DateCreator;
import utilities.Input;
import utilities.Printer;

public class AddServiceAction implements IAction {
	private static Logger logger;
	
	static {
		logger = Logger.getLogger(AddServiceAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}
	@Override
	public void execute() throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_SERVICE_PARAMS.toString());
		String[] params = Input.userInput().split(",");
		try {
			Facade.getInstance().addService(new Service(Integer.parseInt(params[0].trim()), params[1].trim(),
					DateCreator.parseString(params[2].trim())));
		} catch (IncorrectParameterException | IncorrectIDEcxeption | NumberFormatException
				| ArrayIndexOutOfBoundsException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();

		}
	}

}
