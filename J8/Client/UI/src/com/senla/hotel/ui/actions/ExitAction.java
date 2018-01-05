package com.senla.hotel.ui.actions;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.exceptions.ActionForceStopException;

public class ExitAction implements IAction {

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
		
	}

}
