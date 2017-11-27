package com.senla.hotel.ui.actions;

import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;

public class OrderExportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Facade facade = Facade.getInstance();
		Order order = SelectOrderAction.getOrder();
		Facade.getInstance().exportOrder(order);
		Facade manager = Facade.getInstance();
		facade.exportClient(manager.getClientByID(order.getClientID()));
		facade.exportRoom(manager.getRoomByID(order.getRoomID()));
		if (!order.getServiceCount().equals(0)) {
			for (Service service : order.getServices()) {
				facade.exportService(service);
			}
		}

	}

}
