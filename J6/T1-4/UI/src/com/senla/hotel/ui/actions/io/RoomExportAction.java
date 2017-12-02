package com.senla.hotel.ui.actions.io;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;


public class RoomExportAction implements IAction {
	private static Logger logger;
	
	static {
		logger = Logger.getLogger(RoomExportAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}
	@Override
	public void execute() throws ActionForceStopException {
		try {
			Facade.getInstance().exportRoom(SelectRoomAction.getRoom());
		} catch (EmptyObjectException e) {
			logger.log(Level.SEVERE,  this.getClass().getName());
			throw new ActionForceStopException();
		}

	}

}
