package com.senla.hotel.ui.actions.printers;

import java.util.GregorianCalendar;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintFreeRoomsCountAction implements IAction {

	@Override
	public void execute() {
		Printer.print(Facade.getInstance().getFreeRoomsCount(new GregorianCalendar().getTime()).toString());
	}

}
