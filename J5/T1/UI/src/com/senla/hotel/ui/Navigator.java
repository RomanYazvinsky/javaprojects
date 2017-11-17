package com.senla.hotel.ui;

import utilities.Printer;

public class Navigator {
	private Menu currentMenu;
	private static Navigator instance;
	private Boolean exit;

	private Navigator(Menu rootMenu) {
		super();
		currentMenu = rootMenu;
		exit = false;
	}

	public static Navigator getInstance(Menu rootMenu) {
		if (instance == null) {
			instance = new Navigator(rootMenu);
		}
		return instance;
	}

	public Boolean isNotEnd() {
		return !exit;
	}

	public void printMenu() {
		Integer i = 1;
		for (MenuItem item : currentMenu.getMenuItems()) {
			Printer.println(i.toString() + ") " + item.getTitle());
			i++;
		}
	}

	public void navigate(Integer index) throws IndexOutOfBoundsException {
		exit = false;
		if (index > 0) {
			MenuItem menuItem = currentMenu.getMenuItems().get(index);
			menuItem.doAction();
			currentMenu = menuItem.getNextMenu();
		} else {
			if (currentMenu == Builder.getInstance().getRootMenu()) {
				currentMenu.getMenuItems().get(0).doAction();
				exit = true;
			} else {
				currentMenu = Builder.getInstance().getRootMenu();
			}
		}
	}

}
