package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintActualClientsAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		ArrayList<Client> clients = (Facade.getInstance().getActualClients(new GregorianCalendar().getTime()));
		if (clients.size() == 0) {
			throw new ActionForceStopException();
		}
		Printer.printEntities(clients);

	}

}
