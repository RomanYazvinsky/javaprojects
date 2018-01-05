package com.senla.hotel.ui.actions.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;

import utilities.Input;
import utilities.Printer;

public class OrderImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(OrderImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		
		try {
			Message request = new Message("importClients", null);
			writer.writeObject(request);
			Message response = (Message) reader.readObject();
			ArrayList<Client> clients = (ArrayList<Client>) response.getData()[0];
			
			request = new Message("importRooms", null);
			writer.writeObject(request);
			response = (Message) reader.readObject();
			ArrayList<Room> rooms = (ArrayList<Room>) response.getData()[0];
			
			request = new Message("importOrders", null);
			writer.writeObject(request);
			response = (Message) reader.readObject();
			ArrayList<Order> orders = (ArrayList<Order>) response.getData()[0];
			
			request = new Message("importServices", null);
			writer.writeObject(request);
			response = (Message) reader.readObject();
			ArrayList<Service> services = (ArrayList<Service>) response.getData()[0];
			Integer i = 1;
			for (Order order : orders) {
				for (Client client : clients) {
					if (client.getId().equals(order.getClient().getId())) {
						request = new Message("addClientWithID", new Object[] { client });
						writer.writeObject(request);
						reader.readObject();
						order.setClient(client);
						break;
					}
				}
				for (Room room : rooms) {
					if (room.getId().equals(order.getRoom().getId())) {
						request = new Message("addRoomWithID", new Object[] { room });
						writer.writeObject(request);
						reader.readObject();
						order.setRoom(room);
						break;
					}
				}
				for (Service service : order.getServices()) {
					for (Service tempService : services) {
						if (service.getId().equals(tempService.getId())) {
							service = tempService;
						}
					}
				}
				Printer.println(i.toString() + ") " + order.toString());
				i++;
			}
			i = Integer.parseInt(Input.userInput()) - 1;
			Order order = orders.get(i);

			request = new Message("deleteOrder", new Object[] { order });
			writer.writeObject(request);
			reader.readObject();
			
			request = new Message("addOrderWithID", new Object[] { order });
			writer.writeObject(request);
			reader.readObject();

		} catch (ClassNotFoundException | IOException e) {

		}
	}

}
