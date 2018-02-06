package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.AEntity;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.io.ServiceImportAction;

import utilities.Printer;

public class PrintFreeRoomsAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(ServiceImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}
	@Override
	public void execute() throws ActionForceStopException {
		ArrayList<? extends AEntity> entities = Facade.getInstance().getFreeRooms(new GregorianCalendar().getTime());
		if (entities.size() == 0) {
			logger.log(Level.SEVERE, new EmptyObjectException().getMessage());
			throw new ActionForceStopException();
		}
		Printer.printEntities(entities);
	}

}
