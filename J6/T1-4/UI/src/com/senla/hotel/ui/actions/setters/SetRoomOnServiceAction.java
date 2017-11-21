package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.NullException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.LogWriter;
import utilities.Printer;

public class SetRoomOnServiceAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		try {
			if (!Facade.getInstance().setRoomStatus(SelectRoomAction.getRoom(), RoomStatus.ONSERVICE_NOW)) {
				Printer.print("Access denied");
			}

		} catch (NullException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}
	}

}
