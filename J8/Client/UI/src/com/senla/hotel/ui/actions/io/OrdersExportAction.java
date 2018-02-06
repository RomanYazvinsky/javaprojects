package com.senla.hotel.ui.actions.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

public class OrdersExportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(OrdersExportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Message request = new Message(PublicAPI.EXPORT_ORDERS, null);
			writer.writeObject(request);
			reader.readObject();
			
			request = new Message(PublicAPI.EXPORT_CLIENTS, null);
			writer.writeObject(request);
			reader.readObject();
			
			request = new Message(PublicAPI.EXPORT_ROOMS, null);
			writer.writeObject(request);
			reader.readObject();
			
			request = new Message(PublicAPI.EXPORT_SERVICES, null);
			writer.writeObject(request);
			reader.readObject();
		} catch (ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
