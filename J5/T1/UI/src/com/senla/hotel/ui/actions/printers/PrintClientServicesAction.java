package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;

import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Service;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;

import utilities.Printer;

public class PrintClientServicesAction implements IAction {

	@Override
	public void execute() {
		Client client = SelectClientAction.getClient();
		ArrayList<Service> services = Facade.getInstance().getServicesOfClient(client);
		for (Service service : services) {
			Printer.printService(service);
		}
		Printer.println(Facade.getInstance().getPriceForServices(services).toString());
	}

}
