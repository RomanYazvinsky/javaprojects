package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;

import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintServicesAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		ArrayList<Service> entities =  Facade.getInstance().getServices();
		if (entities.size() == 0) {
			throw new ActionForceStopException();
		}
		for (Service service : entities) {
			Printer.printService(service);
		}
	}

}
