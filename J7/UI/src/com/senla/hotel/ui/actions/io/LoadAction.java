package com.senla.hotel.ui.actions.io;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;

public class LoadAction implements IAction {

	@Override
	public void execute() {
		Facade.getInstance().load();
	}

}
