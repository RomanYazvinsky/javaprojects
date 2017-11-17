package com.senla.hotel.ui.actions.sortprinters;

import com.senla.hotel.entities.Service;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class SortServicesByPriceAction implements IAction {

	@Override
	public void execute() {
		Facade facade = Facade.getInstance();
		for (Service service : facade.sortServicesByPrice(facade.getServices())) {
			Printer.printService(service);
		}
	}

}
