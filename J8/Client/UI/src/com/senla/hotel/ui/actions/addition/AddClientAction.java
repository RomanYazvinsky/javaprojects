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
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

import utilities.Input;
import utilities.Printer;

public class AddClientAction implements IAction {
	private static Logger logger;
	
	static {
		logger = Logger.getLogger(AddClientAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_CLIENT_PARAMS.toString());
		String userInput = Input.userInput();
		try {
			Client client = new Client(userInput);
			Message request = new Message(PublicAPI.ADD_CLIENT, new Object[] {client});
			writer.writeObject(request);
			reader.readObject();
		} catch (NumberFormatException | NullPointerException | IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
