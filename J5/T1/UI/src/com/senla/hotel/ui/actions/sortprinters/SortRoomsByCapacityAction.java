package com.senla.hotel.ui.actions.sortprinters;

import java.util.ArrayList;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class SortRoomsByCapacityAction implements IAction {
	@Override
	public void execute() throws ActionForceStopException {
		Facade facade = Facade.getInstance();
		ArrayList<? extends AEntity> entities = facade.sortRoomsByCapacity(facade.getRooms());
		if (entities.size() == 0) {
			throw new ActionForceStopException();
		}
		Printer.printEntities(entities);
	}

}
