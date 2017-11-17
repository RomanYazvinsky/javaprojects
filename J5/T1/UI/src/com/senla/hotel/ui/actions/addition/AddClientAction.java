package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.entities.Client;
import com.senla.hotel.enums.Messages;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.IDGenerator;
import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class AddClientAction implements IAction {

	@Override
	public void execute() {
		Printer.println(Messages.ASK_FOR_CLIENT_PARAMS.toString());
		String userInput = Input.userInput();
		try {
			Facade.getInstance().addClient(new Client(userInput + " " + IDGenerator.createClientID().toString()));
		} catch (NumberFormatException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
		}
	}

}
