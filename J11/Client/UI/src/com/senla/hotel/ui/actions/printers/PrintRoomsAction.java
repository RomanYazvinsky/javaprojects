package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PrintRoomsAction implements IAction {
	private static Logger logger = LogManager.getLogger(PrintRoomsAction.class);

	@SuppressWarnings("unchecked")
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {

		try {
			Message request = new Message(PublicAPI.GET_ROOMS);
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			ArrayList<Room> rooms = (ArrayList<Room>) response.getData()[0];
			if (rooms.size() == 0) {
				logger.log(Level.DEBUG, new EmptyObjectException().getMessage());
				throw new ActionForceStopException();
			}
			Printer.printEntities(rooms);
		} catch (ClassNotFoundException | IOException e) {
			logger.log(Level.DEBUG, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
