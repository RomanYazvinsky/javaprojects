package com.senla.hotel.ui.actions.selectors;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.Printer;

public class SelectOrderAction implements IAction {

	private static Order order;
	private static Logger logger;
	static {
		logger = Logger.getLogger(SelectOrderAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute() throws ActionForceStopException {
		try {
			order = Facade.getInstance().getOrderByID(Integer.parseInt(Input.userInput()));
			Printer.printOrder(order);		
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			logger.log(Level.SEVERE,  e.getMessage());
			throw new ActionForceStopException();
		}	
	}
	
	public static Order getOrder() {
		return order;
	}

}
