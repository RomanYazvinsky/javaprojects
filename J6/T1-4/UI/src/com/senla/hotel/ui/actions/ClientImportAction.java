package com.senla.hotel.ui.actions;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;

import utilities.CSVWorker;
import utilities.Input;

public class ClientImportAction implements IAction {
	
	@Override
	public void execute() throws ActionForceStopException {
		String path = Constants.clientDataDir + Input.userInput();
		Client client = CSVWorker.importClient(path);
		Facade.getInstance().deleteClient(client);
		Facade.getInstance().addClientWithID(client);
	}
	

}
