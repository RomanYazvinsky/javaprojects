package com.senla.hotel.ui.actions.addition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.Input;
import utilities.Printer;

public class AddRoomCloneAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(AddRoomCloneAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Printer.print("Enter the room number");
			Integer number = Integer.parseInt(Input.userInput());
			if (number < 1) {
				Printer.print("Incorrect number");
				throw new ActionForceStopException();
			}
			Room room = (Room) SelectRoomAction.getRoom().clone();
			room.setNumber(number);
			Message request = new Message(PublicAPI.ADD_ROOM, new Object[] { room });
			writer.writeObject(request);
			Message response = (Message) reader.readObject();
			Boolean result = (Boolean) response.getData()[0];
			Printer.isSuccessful(result);
		} catch (EmptyObjectException | CloneNotSupportedException | NumberFormatException | IOException
				| ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

}
