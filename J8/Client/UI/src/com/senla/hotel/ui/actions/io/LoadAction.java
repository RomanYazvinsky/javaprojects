package com.senla.hotel.ui.actions.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.message.Message;

public class LoadAction implements IAction {

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) {
		try {
			Message request = new Message("load", null);
			writer.writeObject(request);
			reader.readObject();
		} catch (ClassNotFoundException | IOException e) {

		}
	}

}
