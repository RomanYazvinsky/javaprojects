package com.senla.hotel.ui.actions.sortprinters;

import com.senla.hotel.entities.Order;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class SortOrdersByDateAction implements IAction {

	@Override
	public void execute() {
		Facade facade = Facade.getInstance();
		for (Order order : facade.sortOrdersByDate(facade.getOrders())) {
			Printer.printOrder(order);
		}
	}

}
