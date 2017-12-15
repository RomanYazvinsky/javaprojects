package com.senla.hotel.ui.actions.selectors;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.Printer;

public class SelectServiceAction implements IAction {
	private static Service service;
	private static Logger logger;
	static {
		logger = Logger.getLogger(SelectServiceAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	@Override
	public void execute() throws ActionForceStopException {	
		try {
			service = Facade.getInstance().getServiceByID(Integer.parseInt(Input.userInput()));
			Printer.printService(service);;		
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			logger.log(Level.SEVERE,  e.getMessage());
			throw new ActionForceStopException();
		}	
	}
	
	public static Service getService() {
		return service;
	}

}
