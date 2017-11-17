package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;
import com.senla.hotel.ui.actions.selectors.SelectServiceAction;

public class AddOrderServiceAction implements IAction {

	@Override
	public void execute() {
		SelectOrderAction.getOrder().addService(SelectServiceAction.getService());
	}

}
