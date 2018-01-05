package com.senla.hotel.ui.actions.addition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.DateCreator;
import utilities.Input;
import utilities.Printer;

public class AddOrderAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(AddOrderAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_ORDER_PARAMS.toString());
		String[] params = Input.userInput().split(",");
		try {
			Order order = new Order(SelectRoomAction.getRoom(), SelectClientAction.getClient(),
					DateCreator.parseString(params[0].trim()), DateCreator.parseString(params[1].trim()), null);
			Message request = new Message("addOrder", new Object[] {order});
			writer.writeObject(request);
			Message response = (Message) reader.readObject();
		} catch ( NumberFormatException | EmptyObjectException
				| ArrayIndexOutOfBoundsException | IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
