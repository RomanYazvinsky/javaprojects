package com.senla.hotel.ui.actions.addition;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;
import com.senla.hotel.ui.actions.selectors.SelectServiceAction;

public class AddOrderServiceAction implements IAction {
	private static Logger logger;
	
	static {
		logger = Logger.getLogger(AddOrderServiceAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}
	@Override
	public void execute() throws ActionForceStopException {
		try {
			SelectOrderAction.getOrder().addService(SelectServiceAction.getService());
		} catch (Exception e) {
			logger.log(Level.SEVERE,  this.getClass().getName());
			throw new ActionForceStopException();

		}
	}

}
