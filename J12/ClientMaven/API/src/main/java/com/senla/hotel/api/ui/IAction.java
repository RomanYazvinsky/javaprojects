package com.senla.hotel.api.ui;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.senla.hotel.exceptions.ActionForceStopException;

public interface IAction {
	void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException, com.senla.hotel.exceptions.ActionForceStopException;
}
