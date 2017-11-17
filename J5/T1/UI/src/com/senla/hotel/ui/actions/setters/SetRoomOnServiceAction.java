package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.exceptions.NullFieldException;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.LogWriter;

public class SetRoomOnServiceAction implements IAction {

	@Override
	public void execute() {
		try {
			SelectRoomAction.getRoom().setOnServiceStatus();
		} catch (NullFieldException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
		}
	}

}
