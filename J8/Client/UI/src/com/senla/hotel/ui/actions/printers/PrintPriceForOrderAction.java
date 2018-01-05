package com.senla.hotel.ui.actions.printers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Service;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;

import utilities.Printer;

public class PrintPriceForOrderAction implements IAction {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) {
		Order order = SelectOrderAction.getOrder();
		try {
			Message request = new Message("getPriceForRoom", new Object[] { order });
			writer.writeObject(request);
			Message response = (Message) reader.readObject();

			Integer price = (Integer) response.getData()[0];
			request = new Message("getServicesOfClient", new Object[] { order.getClient() });
			writer.writeObject(request);
			response = (Message) reader.readObject();

			ArrayList<Service> services = (ArrayList<Service>)  response.getData()[0];
			request = new Message("getPriceForServices", new Object[] { services });
			writer.writeObject(request);
			Printer.print(price.toString());
		} catch (ClassNotFoundException | IOException e) {

		}
	}

}
