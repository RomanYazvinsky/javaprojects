package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;

import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintOrdersAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		ArrayList<Order> entities = Facade.getInstance().getOrders();
		if (entities.size() == 0) {
			throw new ActionForceStopException();
		}
		Printer.printOrders(entities);
	}

}
