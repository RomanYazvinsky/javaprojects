package com.senla.hotel.ui.actions;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;

import utilities.CSVWorker;
import utilities.Input;
import utilities.Printer;

public class OrderImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(OrderImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	@Override
	public void execute() throws ActionForceStopException {
		try {
			ArrayList<Order> orders = CSVWorker.importOrder();
			Facade facade = Facade.getInstance();
			Integer i = 1;
			for (Order order : orders) {
				Printer.println(i.toString() + ") " + order.toString());
				i++;
			}
			i = Integer.parseInt(Input.userInput()) - 1;
			Order order = orders.get(i);
			facade.deleteOrder(order);
			facade.addOrderWithID(order);
			for (Client client : CSVWorker.importClient()) {
				if (client.getId().equals(order.getClientId())) {
					facade.addClientWithID(client);
					break;
				}
			}
			for (Room room : CSVWorker.importRoom()) {
				if (room.getId().equals(order.getRoomId())) {
					facade.addRoomWithID(room);
					break;
				}
			}
			for (Service service : order.getServices()) {
				facade.addServiceWithID(service);
			}
		} catch (ActionForceStopException e) {
			logger.log(Level.SEVERE, this.getClass().getName());
			throw e;
		}
	}

}
