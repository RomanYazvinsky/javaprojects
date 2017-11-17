package com.senla.hotel.ui.actions.sortprinters;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class SortFreeRoomsByCapacityAction implements IAction {

	@Override
	public void execute() {
		Facade facade = Facade.getInstance();
		Printer.printEntityList(facade.sortRoomsByCapacity(facade.getFreeRooms()));
	}

}
