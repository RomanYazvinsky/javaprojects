package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintClientsAction implements IAction {
	@Override
	public void execute() {
		Printer.printEntityList(Facade.getInstance().getClients());
	}

}
