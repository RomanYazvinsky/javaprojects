package com.senla.hotel.ui.actions;

import com.senla.hotel.facade.Hotel;

import utilities.Printer;

public class SortRoomsByCapacity implements IAction {

	@Override
	public void execute() {
		Printer.printStringList(Hotel.getInstance().sortRoomsByCapacity());
	}

}
