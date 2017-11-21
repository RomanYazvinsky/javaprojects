package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.NullException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.LogWriter;
import utilities.Printer;

public class PrintLastRoomOrdersAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		try {
			Printer.printOrders(Facade.getInstance().getLastOrdersOfRoom(SelectRoomAction.getRoom()));
		} catch (NullException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();

		}
	}

}
