package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.IEntity;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.io.ServiceImportAction;

import utilities.DateCreator;
import utilities.Input;
import utilities.Printer;

public class PrintFreeRoomsInFutureAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(ServiceImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}
	@Override
	public void execute() throws ActionForceStopException {
		Printer.println("<>Enter the date");
		Date date = DateCreator.parseString(Input.userInput());
		Facade facade = Facade.getInstance();
		ArrayList<? extends IEntity> entities = facade.getFreeRooms(date);
		if (entities.size() == 0) {
			logger.log(Level.SEVERE, new EmptyObjectException().getMessage());
			throw new ActionForceStopException();
		}
		Printer.printEntities(entities);
	}

}
