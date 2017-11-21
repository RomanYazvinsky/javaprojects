package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.entities.Order;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;

import utilities.Printer;

public class PrintPriceForOrderAction implements IAction {

	@Override
	public void execute() {
		Order order = SelectOrderAction.getOrder();
		Facade facade = Facade.getInstance();
		Printer.println(((Integer) (facade.getPriceForRoom(order)
				+ facade.getPriceForServices(facade.getServicesOfClient(facade.getClientByID(order.getClientID())))))
						.toString());
	}

}
