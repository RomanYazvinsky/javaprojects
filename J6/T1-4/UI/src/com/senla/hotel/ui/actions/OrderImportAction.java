package com.senla.hotel.ui.actions;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;

import utilities.CSVWorker;
import utilities.Input;

public class OrderImportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		String path = Constants.orderDataDir + Input.userInput();
		Order order = CSVWorker.importOrder(path);
		Facade facade = Facade.getInstance();
		facade.deleteOrder(order);
		facade.addOrderWithID(order);
		facade.addClientWithID(CSVWorker.importClient(Constants.clientDataDir +order.getClientID() + Constants.extension));
		facade.addRoomWithID(CSVWorker.importRoom(Constants.roomDataDir + order.getRoomID() + Constants.extension));
		if (!order.getServiceCount().equals(0)) {
			for (Service service : order.getServices())
			facade.addServiceWithID(CSVWorker.importService(Constants.serviceDataDir + service.getID() + Constants.extension));
		}
	}

}
