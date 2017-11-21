package com.senla.hotel.ui.actions;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;

import utilities.CSVWorker;
import utilities.Input;

public class ServiceImportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		String path = Constants.serviceDataDir + Input.userInput();
		Service service = CSVWorker.importService(path);
		Facade.getInstance().deleteService(service);
		Facade.getInstance().addServiceWithID(service);
	}

}
