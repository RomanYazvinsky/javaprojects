package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.entities.Room;
import com.senla.hotel.enums.RoomStatus;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.NullException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.LogWriter;
import utilities.Printer;

public class SetRoomUsableAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Room room;
		try {
			room = SelectRoomAction.getRoom();
			if (room.isOnService()) {
				if (!Facade.getInstance().setRoomStatus(room, RoomStatus.FREE_NOW)) {
					Printer.print("Access denied");
				}
			}
		} catch (NullException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}

	}

}
