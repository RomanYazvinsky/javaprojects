package com.senla.hotel.ui;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IBuilder;
import com.senla.hotel.api.internal.IMenuController;
import com.senla.hotel.api.internal.INavigator;
import com.senla.hotel.constants.Constants;

import utilities.DependencyInjector;
import utilities.Input;
import utilities.Printer;

public class MenuController implements IMenuController {
	private static Logger logger;
	private String hello = "EXAMPLE OF SETTING PARAMS: 12, Roma, 17 12 2017, 19 12 2017";
	private IBuilder builder;
	private INavigator navigator;

	static {
		logger = Logger.getLogger(MenuController.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	public MenuController() {
		builder = (IBuilder) DependencyInjector.newInstance(IBuilder.class);
		builder.buildMenu();
		navigator = (INavigator) DependencyInjector.newInstance(INavigator.class);
		navigator.setCurrentMenu(builder.getRootMenu());
	}
	
	public void setIO(ObjectOutputStream writer, ObjectInputStream reader) {
		navigator.setIO(writer, reader);
	}
	
	@Override
	public void run() {
		Printer.println(hello);
		int index = 0;
		do {
			navigator.printMenu();
			try {
				index = Integer.parseInt(Input.userInput()) - 1;
				navigator.navigate(index);
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				logger.log(Level.SEVERE, e.getMessage());
				continue;
			}

		} while (navigator.isNotEnd());
		Input.input.close();
	}
}
