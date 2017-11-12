package com.senla.hotel.ui;

import utilities.Input;

public class MenuController {
	private Builder builder;
	private Navigator navigator;
	
	public MenuController() {
		builder = Builder.getInstance();
		builder.buildMenu();
		navigator = Navigator.getInstance(builder.getRootMenu());
	}
	
	public void run() {
		int index;
		do {
			navigator.printMenu();
			index = Integer.parseInt(Input.userInput()) - 1;
			navigator.navigate(index);
			
		} while(navigator.isEnd());
		Input.input.close();
	}
}
