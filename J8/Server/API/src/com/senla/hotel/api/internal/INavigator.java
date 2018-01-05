package com.senla.hotel.api.internal;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface INavigator {

	void setCurrentMenu(IMenu menu);
	
	void setIO(ObjectOutputStream writer, ObjectInputStream reader);
	
	Boolean isNotEnd();

	void printMenu();

	void navigate(Integer index) throws IndexOutOfBoundsException;

}