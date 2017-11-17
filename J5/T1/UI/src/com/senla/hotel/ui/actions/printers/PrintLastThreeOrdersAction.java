package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.exceptions.NullFieldException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.LogWriter;
import utilities.Printer;

public class PrintLastThreeOrdersAction implements IAction {

	@Override
	public void execute() {
		try {
			Printer.printOrders(Facade.getInstance().getLastOrdersOfRoom(SelectRoomAction.getRoom(), 3));
		} catch (NullFieldException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
		}
	}

}
