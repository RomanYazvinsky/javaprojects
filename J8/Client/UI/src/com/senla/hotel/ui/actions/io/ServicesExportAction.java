package com.senla.hotel.ui.actions.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

public class ServicesExportAction implements IAction {

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		try {
			Message request = new Message("exportServices", null);
			writer.writeObject(request);
			reader.readObject();
		} catch (ClassNotFoundException | IOException e) {

		}
	}

}
