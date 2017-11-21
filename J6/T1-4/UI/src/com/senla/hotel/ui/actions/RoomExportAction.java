package com.senla.hotel.ui.actions;

import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.NullException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.LogWriter;

public class RoomExportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		try {
			Facade.getInstance().exportRoom(SelectRoomAction.getRoom());
		} catch (NullException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}

	}

}
