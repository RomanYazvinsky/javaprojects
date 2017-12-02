package com.senla.hotel.ui.actions.setters;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.Input;

public class SetRoomPriceAction implements IAction {
	private static Logger logger;
	static {
		logger = Logger.getLogger(SetRoomPriceAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	@Override
	public void execute() throws ActionForceStopException {
		try {
			SelectRoomAction.getRoom().setPricePerDay(Integer.parseInt(Input.userInput()));
		} catch (NumberFormatException | IncorrectParameterException | EmptyObjectException e) {
			logger.log(Level.SEVERE,  e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
