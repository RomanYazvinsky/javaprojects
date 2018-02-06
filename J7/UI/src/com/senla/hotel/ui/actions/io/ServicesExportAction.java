package com.senla.hotel.ui.actions.io;

import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

public class ServicesExportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Facade.getInstance().exportServices();
	}

}
