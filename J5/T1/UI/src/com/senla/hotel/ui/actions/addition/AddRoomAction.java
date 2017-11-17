package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.entities.Room;
import com.senla.hotel.enums.Messages;
import com.senla.hotel.enums.RoomStatus;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.IDGenerator;
import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class AddRoomAction implements IAction {

	@Override
	public void execute() {
		Printer.println(Messages.ASK_FOR_ROOM_PARAMS.toString());
		String[] params = Input.userInput().split(",");
		try {
			Facade.getInstance().addRoom(new Room(IDGenerator.createRoomID(), Integer.parseInt(params[0].trim()),
					Integer.parseInt(params[1].trim()), RoomStatus.FREE, Integer.parseInt(params[2].trim())));
		} catch (IncorrectParameterException | IncorrectIDEcxeption | NumberFormatException
				| ArrayIndexOutOfBoundsException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
		}
	}

}
