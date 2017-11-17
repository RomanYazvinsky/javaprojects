package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.NullFieldException;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.LogWriter;

public class SetRoomUsableAction implements IAction {

	@Override
	public void execute() {
		Room room;
		try {
			room = SelectRoomAction.getRoom();
			if (room.isOnService()) {
				room.setUsableStatus();
			}
		} catch (NullFieldException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
		}

	}

}
