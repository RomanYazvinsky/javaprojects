package com.senla.hotel.ui.actions.addition;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.Printer;

public class AddRoomAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(AddRoomAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute() throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_ROOM_PARAMS.toString());
		String[] params = Input.userInput().split(",");
		try {
			Room room = new Room(Integer.parseInt(params[0].trim()), Integer.parseInt(params[1].trim()),
					Integer.parseInt(params[2].trim()), Integer.parseInt(params[3].trim()));
			Facade.getInstance().addRoom(room);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
