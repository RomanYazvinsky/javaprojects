package com.senla.hotel.ui.actions.printers;

import java.util.Date;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.DateCreator;
import utilities.Input;
import utilities.Printer;

public class PrintFreeRoomsInFutureAction implements IAction {

	@Override
	public void execute() {
		Date date = DateCreator.parseString(Input.userInput());
		Facade facade = Facade.getInstance();
		Printer.printEntityList(facade.getFreeRoomByDate(date));
	}

}
