package com.senla.hotel.ui.actions;

import com.senla.hotel.facade.Facade;

public class ResetAction implements IAction {

	@Override
	public void execute() {
		Facade.getInstance().reset();
	}

}
