package com.senla.hotel.ui.actions.addition;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.Printer;

public class AddRoomCloneAction implements IAction {
	private static Logger logger;

	
	static {
		logger = Logger.getLogger(AddRoomCloneAction.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}
	
	@Override
	public void execute() throws ActionForceStopException {
		try {
			Printer.isSuccessful(Facade.getInstance().addRoom((Room) SelectRoomAction.getRoom().clone()));
		} catch (EmptyObjectException | CloneNotSupportedException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

}
