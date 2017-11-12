package com.senla.hotel.ui.actions;

import java.util.GregorianCalendar;

import com.senla.hotel.facade.Hotel;

import utilities.DateCreator;
import utilities.Input;
import utilities.Printer;

public class SetParametersAction implements IAction {

	@Override
	public void execute() {
		String[] paths = new String[4];
		Printer.askForOrderFile();
		paths[0] = Input.userInput(); 
		Printer.askForClientFile();
		paths[1] = Input.userInput(); 
		Printer.askForRoomFile();
		paths[2] = Input.userInput(); 
		Printer.askForServiceFile();
		paths[3] = Input.userInput(); 
		Printer.askForDate();
		GregorianCalendar now = DateCreator.parseString(Input.userInput());
		Hotel.getInstance().setParameters(paths, now);
	}

}
