package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;
import com.senla.hotel.ui.actions.selectors.SelectServiceAction;

import utilities.LogWriter;

public class AddOrderServiceAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		try {
			SelectOrderAction.getOrder().addService(SelectServiceAction.getService());
		} catch (Exception e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();

		}
	}

}
