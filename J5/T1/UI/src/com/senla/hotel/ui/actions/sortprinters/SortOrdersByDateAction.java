package com.senla.hotel.ui.actions.sortprinters;

import java.util.ArrayList;

import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class SortOrdersByDateAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Facade facade = Facade.getInstance();
		ArrayList<Order> entities = facade.sortOrdersByDate(facade.getOrders());
		if (entities.size() == 0) {
			throw new ActionForceStopException();
		}
		for (Order order : entities ) {
			Printer.printOrder(order);
		}
	}

}
