package com.senla.hotel.ui;

import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class MenuController {
	private Builder builder;
	private Navigator navigator;
	private LogWriter log;

	public MenuController() {
		builder = Builder.getInstance();
		builder.buildMenu();
		navigator = Navigator.getInstance(builder.getRootMenu());
		log = LogWriter.getInstance();
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
				log.log(e, this.getClass().getName());
				continue;
			}

		} while (navigator.isNotEnd());
		Input.input.close();
	}
}
