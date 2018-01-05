package com.senla.hotel.ui.actions.printers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.message.Message;

import utilities.Printer;

public class PrintFreeRoomsCountAction implements IAction {

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) {
		try {
			Message request = new Message("getFreeRoomsCount", new Object[] { new GregorianCalendar().getTime() });
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			Integer count = (Integer)  response.getData()[0];
			Printer.print(count.toString());

		} catch (ClassNotFoundException | IOException e) {

		}
	}

}
