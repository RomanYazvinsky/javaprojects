package com.senla.hotel.api.internal;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface IMenuController {

	void setIO(ObjectOutputStream writer, ObjectInputStream reader);
	
	void run();

}