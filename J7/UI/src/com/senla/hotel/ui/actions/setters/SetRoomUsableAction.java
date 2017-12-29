package com.senla.hotel.ui.actions.setters;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.Printer;

public class SetRoomUsableAction implements IAction {
	private static Logger logger;
	static {
		logger = Logger.getLogger(SetRoomUsableAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

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
		} catch (EmptyObjectException e) {
			logger.log(Level.SEVERE,  e.getMessage());
			throw new ActionForceStopException();
		}

	}

}
