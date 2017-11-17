package com.senla.hotel.ui.actions.selectors;

import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class SelectServiceAction implements IAction {
	private static Service service;
	@Override
	public void execute() throws ActionForceStopException {	
		try {
			service = Facade.getInstance().getServiceByID(Integer.parseInt(Input.userInput()));
			Printer.printService(service);;		
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}	
	}
	
	public static Service getService() {
		return service;
	}

}
