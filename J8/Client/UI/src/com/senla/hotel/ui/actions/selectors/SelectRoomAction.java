package com.senla.hotel.ui.actions.selectors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;

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
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Message request = new Message("getRoomByID", new Object[] { Integer.parseInt(Input.userInput()) });
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			room = (Room)  response.getData()[0];
			Printer.printRoom(room);
		} catch (NumberFormatException | IndexOutOfBoundsException | ClassNotFoundException | IOException e) {
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
