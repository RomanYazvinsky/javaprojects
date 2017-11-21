package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.constants.Messages;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class AddRoomAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_ROOM_PARAMS.toString());
		String[] params = Input.userInput().split(",");
		try {
			Room room = new Room(Integer.parseInt(params[0].trim()),
					Integer.parseInt(params[1].trim()), RoomStatus.FREE_NOW, Integer.parseInt(params[2].trim()));
			Facade.getInstance().addRoom(room);
		} catch (IncorrectParameterException | IncorrectIDEcxeption | NumberFormatException
				| ArrayIndexOutOfBoundsException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}
	}

}
