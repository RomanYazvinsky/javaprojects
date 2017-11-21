package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectServiceAction;

import utilities.Input;
import utilities.LogWriter;

public class SetServicePriceAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		try {
			SelectServiceAction.getService().setPrice(Integer.parseInt(Input.userInput()));
		} catch (NumberFormatException | IncorrectParameterException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}
	}

}
