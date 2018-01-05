package com.senla.hotel.ui.actions.setters;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.ui.actions.selectors.SelectServiceAction;

import utilities.Input;

public class SetServicePriceAction implements IAction {
	private static Logger logger;
	static {
		logger = Logger.getLogger(SetServicePriceAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			SelectServiceAction.getService().setPrice(Integer.parseInt(Input.userInput()));
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
