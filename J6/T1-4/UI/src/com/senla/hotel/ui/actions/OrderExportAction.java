package com.senla.hotel.ui.actions;

import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;

public class OrderExportAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Facade.getInstance().exportOrder(SelectOrderAction.getOrder());

	}

}
