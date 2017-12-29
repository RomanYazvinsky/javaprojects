package com.senla.hotel.ui.actions.selectors;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.Printer;

public class SelectRoomAction implements IAction {
	private static Room room;
	private static Logger logger;
	static {
		logger = Logger.getLogger(SelectRoomAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute() throws ActionForceStopException {
		try {
			room = Facade.getInstance().getRoomByID(Integer.parseInt(Input.userInput()));
			Printer.printRoom(room);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

	public static Room getRoom() throws EmptyObjectException {
		if (room == null) {
			throw new EmptyObjectException();
		}
		return room;
	}

}
