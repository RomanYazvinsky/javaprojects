package com.senla.hotel.ui.actions.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

import utilities.Input;
import utilities.Printer;

public class RoomImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(RoomImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {

		try {
			Message request = new Message("importRooms", null);
			writer.writeObject(request);
			Message response = (Message) reader.readObject();
			ArrayList<Room> rooms = (ArrayList<Room>) response.getData()[0];
			Integer i = 1;
			for (Room room : rooms) {
				Printer.println(i.toString() + ") " + room.toString());
				i++;
			}
			i = Integer.parseInt(Input.userInput()) - 1;
			Room room = rooms.get(i);

			request = new Message("deleteRoom", new Object[] { room });
			writer.writeObject(request);
			reader.readObject();
			request = new Message("addRoomWithID", new Object[] { room });
			writer.writeObject(request);
			reader.readObject();

		} catch (ClassNotFoundException | IOException e) {

		}

	}

}
