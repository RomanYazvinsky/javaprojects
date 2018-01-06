package com.senla.hotel.ui.actions.addition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

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
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_ROOM_PARAMS.toString());
		String[] params = Input.userInput().split(",");
		try  {
			Room room = new Room(Integer.parseInt(params[0].trim()), Integer.parseInt(params[1].trim()),
					Integer.parseInt(params[2].trim()), Integer.parseInt(params[3].trim()));
			Message request = new Message(PublicAPI.ADD_ROOM, new Object[] {room});
			writer.writeObject(request);
			reader.readObject();
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
