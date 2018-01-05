package com.senla.hotel.ui.actions.setters;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.Printer;

public class SetRoomOnServiceAction implements IAction {
	private static Logger logger;
	static {
		logger = Logger.getLogger(SetRoomOnServiceAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Message request = new Message("setRoomStatus",
					new Object[] { SelectRoomAction.getRoom(), RoomStatus.ONSERVICE_NOW });
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			Boolean result = (Boolean)  response.getData()[0];
			if (!result) {
				Printer.print("Access denied");
			}

		} catch (EmptyObjectException | ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
