package com.senla.hotel.ui.actions.printers;

import java.util.GregorianCalendar;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintActualClientsAction implements IAction {

	@Override
	public void execute() {
		Printer.printOrders(Facade.getInstance().getActualOrders(new GregorianCalendar().getTime()));
	}

}
