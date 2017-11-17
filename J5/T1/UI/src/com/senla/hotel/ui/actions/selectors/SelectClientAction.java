package com.senla.hotel.ui.actions.selectors;

import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class SelectClientAction implements IAction {
	private static Client client;

	@Override
	public void execute() throws ActionForceStopException {
		try {
			client = Facade.getInstance().getClientByID(Integer.parseInt(Input.userInput()));
			Printer.printClient(client);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();

		}
	}

	public static Client getClient() {
		return client;
	}

}
