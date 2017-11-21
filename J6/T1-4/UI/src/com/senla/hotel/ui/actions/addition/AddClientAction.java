package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.IncorrectNameException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class AddClientAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_CLIENT_PARAMS.toString());
		String userInput = Input.userInput();
		try {
			Facade.getInstance().addClient(new Client(userInput));
		} catch (NumberFormatException | NullPointerException | IncorrectNameException  e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}
	}

}
