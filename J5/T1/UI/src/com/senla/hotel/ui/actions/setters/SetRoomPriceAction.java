package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.exceptions.NullFieldException;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.Input;
import utilities.LogWriter;

public class SetRoomPriceAction implements IAction {

	@Override
	public void execute() {
		try {
			SelectRoomAction.getRoom().setPricePerDay(Integer.parseInt(Input.userInput()));
		} catch (NumberFormatException | IncorrectParameterException | NullFieldException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
		}
	}

}
