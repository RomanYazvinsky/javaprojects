package com.senla.hotel.ui.actions;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;

import utilities.CSVWorker;
import utilities.Input;

public class RoomImportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		String path = Constants.roomDataDir + Input.userInput();
		Room room = CSVWorker.importRoom(path);
		Facade.getInstance().deleteRoom(room);
		Facade.getInstance().addRoomWithID(room);
	}

}
