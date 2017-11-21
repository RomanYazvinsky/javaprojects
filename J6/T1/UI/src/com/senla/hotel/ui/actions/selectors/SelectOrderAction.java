package com.senla.hotel.ui.actions.selectors;

import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class SelectOrderAction implements IAction {

	private static Order order;
	
	@Override
	public void execute() throws ActionForceStopException {
		try {
			order = Facade.getInstance().getOrderByID(Integer.parseInt(Input.userInput()));
			Printer.printOrder(order);		
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}	
	}
	
	public static Order getOrder() {
		return order;
	}

}
