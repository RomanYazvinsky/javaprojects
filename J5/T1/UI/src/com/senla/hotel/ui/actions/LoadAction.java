package com.senla.hotel.ui.actions;

import com.senla.hotel.facade.Hotel;

public class LoadAction implements IAction {

	@Override
	public void execute() {
		Hotel.getInstance().load();
	}

}
