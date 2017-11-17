package com.senla.hotel.ui.actions.printers;

import java.util.GregorianCalendar;

import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Room;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Printer;

public class PrintClientsAndRoomsAction implements IAction {

	@Override
	public void execute() {
		Facade facade = Facade.getInstance();
		for (Client client : facade.getClients()) {
			Printer.printClient(client);
			Room room = facade
					.getRoomByID(facade.getActualOrder(client, new GregorianCalendar().getTime()).getRoomID());
			if (room != null) {
				Printer.printRoom(room);
			}
		}
	}

}
