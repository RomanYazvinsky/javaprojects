package com.senla.hotel.ui.actions.printers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;

import utilities.DateCreator;
import utilities.Input;
import utilities.Printer;

public class PrintFreeRoomsInFutureAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(PrintFreeRoomsInFutureAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		Printer.println("<>Enter the date");
		Date date = DateCreator.parseString(Input.userInput());
		try {
			Message request = new Message(PublicAPI.GET_FREE_ROOMS, new Object[] { date });
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			ArrayList<Room> rooms = (ArrayList<Room>) response.getData()[0];
			if (rooms.size() == 0) {
				logger.log(Level.SEVERE, new EmptyObjectException().getMessage());
				throw new ActionForceStopException();
			}
			Printer.printEntities(rooms);
		} catch (ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
