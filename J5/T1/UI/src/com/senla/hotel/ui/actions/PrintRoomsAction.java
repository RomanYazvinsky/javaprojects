package com.senla.hotel.ui.actions;

import com.senla.hotel.facade.Hotel;

import utilities.Printer;

public class PrintRoomsAction implements IAction {
	@Override
	public void execute() {
		Printer.printStringList(Hotel.getInstance().getRooms());
	}

}
