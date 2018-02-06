package com.senla.hotel.ui.actions.io;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.Printer;

public class OrderImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(OrderImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@Override
	public void execute() throws ActionForceStopException {
		Facade facade = Facade.getInstance();
		ArrayList<Order> orders = facade.importOrders();
		ArrayList<Client> clients = facade.importClients();
		ArrayList<Room> rooms = facade.importRooms();
		ArrayList<Service> services = facade.importServices();
		Integer i = 1;
		for (Order order : orders) {
			for (Client client : clients) {
				if (client.getId().equals(order.getClient().getId())) {
					facade.addClientWithID(client);
					order.setClient(client);
					break;
				}
			}
			for (Room room : rooms) {
				if (room.getId().equals(order.getRoom().getId())) {
					facade.addRoomWithID(room);
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
		facade.deleteOrder(order);
		facade.addOrderWithID(order);
	}

}
