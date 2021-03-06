package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintFreeRoomsAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		ArrayList<? extends AEntity> entities = Facade.getInstance().getFreeRooms(new GregorianCalendar().getTime());
		if (entities.size() == 0) {
			throw new ActionForceStopException();
		}
		Printer.printEntities(entities);
	}

}
