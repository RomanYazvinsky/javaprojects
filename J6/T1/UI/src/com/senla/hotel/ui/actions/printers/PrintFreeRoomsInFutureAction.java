package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;
import java.util.Date;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.DateCreator;
import utilities.Input;
import utilities.Printer;

public class PrintFreeRoomsInFutureAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Printer.println("<>Enter the date");
		Date date = DateCreator.parseString(Input.userInput());
		Facade facade = Facade.getInstance();
		ArrayList<? extends AEntity> entities = facade.getFreeRooms(date);
		if (entities.size() == 0) {
			throw new ActionForceStopException();
		}
		Printer.printEntities(entities);
	}

}
