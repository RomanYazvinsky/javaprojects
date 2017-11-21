package com.senla.hotel.ui.actions;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;

import utilities.CSVWorker;
import utilities.Input;

public class OrderImportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		String path = Constants.orderDataDir + Input.userInput();
		Order order = CSVWorker.importOrder(path);
		Facade.getInstance().deleteOrder(order);
		Facade.getInstance().addOrderWithID(order);
	}

}
