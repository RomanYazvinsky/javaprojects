package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.entities.Service;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintServicesAction implements IAction {

	@Override
	public void execute() {
		for (Service service : Facade.getInstance().getServices()) {
			Printer.printService(service);
		}
	}

}
