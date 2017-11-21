package com.senla.hotel.ui.actions.printers;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintAvailableRoomsAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		ArrayList<Room> rooms = Facade.getInstance().getFreeRooms(new GregorianCalendar().getTime());
		if(rooms.size() == 0) {
			throw new ActionForceStopException();
		}
		Printer.printEntities(rooms);
	}

}
