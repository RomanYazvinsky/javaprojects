package com.senla.hotel.ui.actions.selectors;

import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.NullFieldException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class SelectRoomAction implements IAction {
	private static Room room;
	@Override
	public void execute() throws ActionForceStopException {	
		try {
			room = Facade.getInstance().getRoomByID(Integer.parseInt(Input.userInput()));
			Printer.printRoom(room);		
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}	
	}
	
	public static Room getRoom() throws NullFieldException {
		if (room == null) {
			throw new NullFieldException();
		}
		return room;
	}

}
