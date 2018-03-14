package com.senla.hotel.api.ui;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.senla.hotel.exceptions.ActionForceStopException;

public interface IMenuItem {

	void setNextMenu(IMenu menu);

	String getTitle();

	IAction getAction();

	IMenu getNextMenu();

	void doAction(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException;

}