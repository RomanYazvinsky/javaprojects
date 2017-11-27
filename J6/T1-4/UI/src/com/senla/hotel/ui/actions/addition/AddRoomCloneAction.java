package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.NullException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.LogWriter;
import utilities.Printer;

public class AddRoomCloneAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		try {
			Printer.isSuccessful(Facade.getInstance().addRoom((Room) SelectRoomAction.getRoom().clone()));
		} catch (NullException | CloneNotSupportedException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
		}
	}

}
