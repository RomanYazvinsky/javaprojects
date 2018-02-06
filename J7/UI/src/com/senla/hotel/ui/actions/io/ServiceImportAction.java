package com.senla.hotel.ui.actions.io;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.Printer;

public class ServiceImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(ServiceImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute() throws ActionForceStopException {

		ArrayList<Service> services = Facade.getInstance().importServices();
		Integer i = 1;
		for (Service service : services) {
			Printer.println(i.toString() + ") " + service.toString());
			i++;
		}
		i = Integer.parseInt(Input.userInput()) - 1;
		Service service = services.get(i);

		Facade.getInstance().deleteService(service);
		Facade.getInstance().addServiceWithID(service);

	}

}
