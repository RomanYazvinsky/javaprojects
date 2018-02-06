package com.senla.hotel.ui.actions.printers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;

import utilities.Printer;

public class PrintPriceForOrderAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(PrintPriceForOrderAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		Order order = SelectOrderAction.getOrder();
		try {
			Message request = new Message(PublicAPI.GET_PRICE_FOR_ROOM, new Object[] { order });
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			Integer price = (Integer) response.getData()[0];
			request = new Message(PublicAPI.GET_SERVICES_OF_CLIENT, new Object[] { order.getClient() });
			writer.writeObject(request);
			response = (Message) reader.readObject();

			ArrayList<Service> services = (ArrayList<Service>)  response.getData()[0];
			request = new Message(PublicAPI.GET_PRICE_FOR_SERVICES, new Object[] { services });
			writer.writeObject(request);
			Printer.print(price.toString());
		} catch (ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
