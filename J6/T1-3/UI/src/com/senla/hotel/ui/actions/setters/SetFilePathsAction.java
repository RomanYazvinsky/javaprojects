package com.senla.hotel.ui.actions.setters;

import java.io.IOException;

import com.senla.hotel.enums.Messages;
import com.senla.hotel.enums.PropertyNames;
import com.senla.hotel.properties.HotelProperties;
import com.senla.hotel.ui.actions.IAction;

import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class SetFilePathsAction implements IAction {

	@Override
	public void execute() {
		HotelProperties properties;
		try {
			properties = HotelProperties.getInstance();
			Printer.println(Messages.ASK_FOR_ORDER_FILE.toString());
			properties.setProperty(PropertyNames.ORDER_FILENAME.toString(),Input.userInput());
			Printer.println(Messages.ASK_FOR_CLIENT_FILE.toString());
			properties.setProperty(PropertyNames.CLIENT_FILENAME.toString(),Input.userInput());
			Printer.println(Messages.ASK_FOR_ROOM_FILE.toString());
			properties.setProperty(PropertyNames.ROOM_FILENAME.toString(),Input.userInput());
			Printer.println(Messages.ASK_FOR_SERVICE_FILE.toString());
			properties.setProperty(PropertyNames.SERVICE_FILENAME.toString(),Input.userInput());
		} catch (IOException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
		}
		
	}

}
