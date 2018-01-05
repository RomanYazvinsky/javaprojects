package com.senla.hotel.ui.actions;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.senla.hotel.api.internal.IAction;

public class NoAction implements IAction{

	@Override
	public void execute(ObjectOutputStream writer, ObjectInputStream reader) {
		
	}

}
