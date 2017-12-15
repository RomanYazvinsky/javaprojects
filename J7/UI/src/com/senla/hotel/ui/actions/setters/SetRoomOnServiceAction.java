package com.senla.hotel.ui.actions.setters;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.Printer;

public class SetRoomOnServiceAction implements IAction {
	private static Logger logger;
	static {
		logger = Logger.getLogger(SetRoomOnServiceAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	@Override
	public void execute() throws ActionForceStopException {
		try {
			if (!Facade.getInstance().setRoomStatus(SelectRoomAction.getRoom(), RoomStatus.ONSERVICE_NOW)) {
				Printer.print("Access denied");
			}

		} catch (EmptyObjectException e) {
			logger.log(Level.SEVERE,  e.getMessage());
			throw new ActionForceStopException();
		}
	}

}
