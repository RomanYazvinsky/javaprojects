package com.senla.hotel.ui.actions.io;

import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

public class OrdersExportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Facade.getInstance().exportOrders();
		Facade.getInstance().exportClients();
		Facade.getInstance().exportRooms();
		Facade.getInstance().exportServices();
	}

}
