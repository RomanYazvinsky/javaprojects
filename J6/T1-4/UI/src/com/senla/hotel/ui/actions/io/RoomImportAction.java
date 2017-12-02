package com.senla.hotel.ui.actions.io;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.CSVWorker;
import utilities.Input;
import utilities.Printer;

public class RoomImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(RoomImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	@Override
	public void execute() throws ActionForceStopException {
		try {
			ArrayList<Room> rooms = CSVWorker.importRooms();
			Integer i = 1;
			for (Room room : rooms) {
				Printer.println(i.toString() + ") " + room.toString());
				i++;
			}
			i = Integer.parseInt(Input.userInput()) - 1;
			Room room = rooms.get(i);

			Facade.getInstance().deleteRoom(room);
			Facade.getInstance().addRoomWithID(room);
		} catch (ActionForceStopException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

}
