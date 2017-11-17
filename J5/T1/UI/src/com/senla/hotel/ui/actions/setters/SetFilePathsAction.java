package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.enums.Messages;
import com.senla.hotel.facade.Parameters;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.Printer;

public class SetFilePathsAction implements IAction {

	@Override
	public void execute() {
		String[] paths = new String[4];
		Printer.println(Messages.ASK_FOR_ORDER_FILE.toString());
		paths[0] = Input.userInput();
		Printer.println(Messages.ASK_FOR_CLIENT_FILE.toString());
		paths[1] = Input.userInput();
		Printer.println(Messages.ASK_FOR_ROOM_FILE.toString());
		paths[2] = Input.userInput();
		Printer.println(Messages.ASK_FOR_SERVICE_FILE.toString());
		paths[3] = Input.userInput();
		Parameters.setPaths(paths);
	}

}
