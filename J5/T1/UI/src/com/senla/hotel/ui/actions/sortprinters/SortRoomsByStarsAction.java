package com.senla.hotel.ui.actions.sortprinters;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class SortRoomsByStarsAction implements IAction {

	@Override
	public void execute() {
		Facade facade = Facade.getInstance();
		Printer.printEntityList(facade.sortRoomsByStar(facade.getRooms()));
	}

}
