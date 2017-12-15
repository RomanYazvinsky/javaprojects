package com.senla.hotel.ui.actions.sortprinters;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.senla.hotel.entities.IEntity;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class SortFreeRoomsByStarsAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Facade facade = Facade.getInstance();
		ArrayList<? extends IEntity> entities = facade.sortRoomsByStar(facade.getFreeRooms(new GregorianCalendar().getTime()));
		if (entities.size() == 0) {
			throw new ActionForceStopException();
		}
		Printer.printEntities(entities);
	}

}
