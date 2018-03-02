package com.senla.hotel.ui.actions;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ExitAction implements IAction {

	private static Logger logger = LogManager.getLogger(ExitAction.class);

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Message request = new Message(PublicAPI.SAVE);
			writer.writeObject(request);
			reader.readObject();
		} catch (NumberFormatException | NullPointerException | IOException | ClassNotFoundException e) {
			logger.log(Level.DEBUG, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
