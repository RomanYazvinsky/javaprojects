package com.senla.hotel.ui.actions.sortprinters;

import java.util.ArrayList;

import com.senla.hotel.entities.IEntity;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class SortRoomsByPriceAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Facade facade = Facade.getInstance();
		ArrayList<? extends IEntity> entities = facade.sortRoomsByPrice(facade.getRooms());
		if (entities.size() == 0) {
			throw new ActionForceStopException();
		}
		Printer.printEntities(entities);
	}

}
