package com.senla.hotel.ui.actions;

import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;

public class ClientExportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Facade.getInstance().exportClient(SelectClientAction.getClient());
	}

}
