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
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

import utilities.DateCreator;
import utilities.Input;
import utilities.Printer;

public class AddServiceAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(AddServiceAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_SERVICE_PARAMS.toString());
		String[] params = Input.userInput().split(",");
		try  {
			Service service = new Service(Integer.parseInt(params[0].trim()), params[1].trim(),
					DateCreator.parseString(params[2].trim()));
			Message request = new Message(PublicAPI.ADD_SERVICE, new Object[] { service });
			writer.writeObject(request);
			reader.readObject();
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();

		}
	}

}
