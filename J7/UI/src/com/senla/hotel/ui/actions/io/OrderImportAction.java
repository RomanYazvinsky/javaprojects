package com.senla.hotel.ui.actions.io;

import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.ui.actions.IAction;

public class OrderImportAction implements IAction {
	private static Logger logger;

	static {
		logger = Logger.getLogger(OrderImportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	@Override
	public void execute() throws ActionForceStopException {
		/*try {
			ArrayList<Order> orders = CSVWorker.importOrders();
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
			for (Client client : CSVWorker.importClients()) {
				if (client.getId().equals(order.getClient())) {
					facade.addClientWithID(client);
					break;
				}
			}
			for (Room room : CSVWorker.importRooms()) {
				if (room.getId().equals(order.getRoom())) {
					facade.addRoomWithID(room);
					break;
				}
			}
			for (Service service : order.getServices()) {
				facade.addServiceWithID(service);
			}
		} catch (ActionForceStopException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}*/
	}

}
