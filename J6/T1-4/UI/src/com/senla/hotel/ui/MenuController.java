package com.senla.hotel.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;

import utilities.Input;
import utilities.Printer;

public class MenuController {
	private static Logger logger;
	private Builder builder;
	private Navigator navigator;

	
	static {
		logger = Logger.getLogger(MenuController.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}
	
	public MenuController() {
		builder = Builder.getInstance();
		builder.buildMenu();
		navigator = Navigator.getInstance(builder.getRootMenu());
	}

	public void run() {
		
		Printer.println("EXAMPLE OF SETTING PARAMS: 12, Roma, 17 12 2017, 19 12 2017");
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
