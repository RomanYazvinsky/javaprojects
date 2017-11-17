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
		Printer.print(Facade.getInstance().getPriceForRoom(order).toString());
	}

}
