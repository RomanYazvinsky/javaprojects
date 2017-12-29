package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.io.ServiceImportAction;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;

import utilities.Printer;

public class PrintClientServicesAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(ServiceImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}
	@Override
	public void execute() throws ActionForceStopException {
		Client client = SelectClientAction.getClient();
		ArrayList<Service> services = Facade.getInstance().getServicesOfClient(client);
		if (services.size()==0) {
			logger.log(Level.SEVERE, new EmptyObjectException().getMessage());
			throw new ActionForceStopException();
		}
		for (Service service : services) {
			Printer.printService(service);
		}
		Printer.println(Facade.getInstance().getPriceForServices(services).toString());
	}

}
