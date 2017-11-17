package com.senla.hotel.ui.actions;

import com.senla.hotel.exceptions.ActionForceStopException;

public interface IAction {
	void execute() throws ActionForceStopException;
}
