package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.entities.Room;
import com.senla.hotel.enums.RoomStatus;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.NullFieldException;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.LogWriter;

public class SetRoomUsableAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Room room;
		try {
			room = SelectRoomAction.getRoom();
			if (room.isOnService()) {
				room.setStatus(RoomStatus.FREE_NOW);;
			}
		} catch (NullFieldException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}

	}

}
