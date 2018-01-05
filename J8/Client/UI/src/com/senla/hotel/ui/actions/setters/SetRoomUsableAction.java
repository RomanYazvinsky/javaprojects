package com.senla.hotel.ui.actions.setters;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.Printer;

public class SetRoomUsableAction implements IAction {
	private static Logger logger;
	static {
		logger = Logger.getLogger(SetRoomUsableAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		Room room;
		try {
			room = SelectRoomAction.getRoom();
			if (room.isOnService()) {
				Message request = new Message("setRoomStatus",
						new Object[] { SelectRoomAction.getRoom(), RoomStatus.FREE_NOW });
				writer.writeObject(request);
				Message response = (Message) reader.readObject();

				Boolean result = (Boolean) response.getData()[0];
				if (!result) {
					Printer.print("Access denied");
				}
			}
		} catch (EmptyObjectException | ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}

	}

}
