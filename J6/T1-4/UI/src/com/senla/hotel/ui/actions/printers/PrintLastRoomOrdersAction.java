package com.senla.hotel.ui.actions.printers;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.Printer;

public class PrintLastRoomOrdersAction implements IAction {
	private static Logger logger;
	
	static {
		logger = Logger.getLogger(PrintLastRoomOrdersAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}
	@Override
	public void execute() throws ActionForceStopException {
		try {
			Printer.printOrders(Facade.getInstance().getLastOrdersOfRoom(SelectRoomAction.getRoom()));
		} catch (EmptyObjectException e) {
			logger.log(Level.SEVERE,  this.getClass().getName());
			throw new ActionForceStopException();

		}
	}

}
