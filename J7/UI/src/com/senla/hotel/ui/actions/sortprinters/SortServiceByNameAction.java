package com.senla.hotel.ui.actions.sortprinters;

import java.util.ArrayList;

import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class SortServiceByNameAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Facade facade = Facade.getInstance();
		ArrayList<Service> entities = facade.sortServicesByName(facade.getServices());
		if (entities.size() == 0) {
			throw new ActionForceStopException();
		}
		for (Service service : entities) {
			Printer.printService(service);
		}
	}

}
