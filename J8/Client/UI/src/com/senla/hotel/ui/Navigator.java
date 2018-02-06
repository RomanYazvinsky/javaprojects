package com.senla.hotel.ui;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IMenu;
import com.senla.hotel.api.internal.IMenuItem;
import com.senla.hotel.api.internal.INavigator;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;

import utilities.Printer;

public class Navigator implements INavigator {
	private static Logger logger;
	private static INavigator instance;
	private IMenu currentMenu;
	private Boolean exit;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	
	static {
		logger = Logger.getLogger(Navigator.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	private Navigator() {
		super();
		exit = false;
	}

	public static INavigator getInstance() {
		if (instance == null) {
			instance = new Navigator();
		}
		return instance;
	}

	public void setIO(ObjectOutputStream writer, ObjectInputStream reader) {
		this.reader = reader;
		this.writer = writer;
	}
	
	public void setCurrentMenu(IMenu menu) {
		currentMenu = menu;
	}
	
	@Override
	public Boolean isNotEnd() {
		return !exit;
	}

	@Override
	public void printMenu() {
		Integer i = 1;
		for (IMenuItem item : currentMenu.getMenuItems()) {
			Printer.println(i.toString() + ") " + item.getTitle());
			i++;
		}
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.ui.INavigator#navigate(java.lang.Integer)
	 */
	@Override
	public void navigate(Integer index) throws IndexOutOfBoundsException {
		exit = false;
		if (index > 0) {
			IMenuItem menuItem = currentMenu.getMenuItems().get(index);
			try {
				menuItem.doAction(writer, reader);
				currentMenu = menuItem.getNextMenu();
			} catch (ActionForceStopException e) {
				logger.log(Level.SEVERE, e.getMessage());
				currentMenu = Builder.getInstance().getRootMenu();
			}
		} else {
			try {
				if (currentMenu == Builder.getInstance().getRootMenu()) {
					currentMenu.getMenuItems().get(0).doAction(writer, reader);
					exit = true;
				} else {
					currentMenu = Builder.getInstance().getRootMenu();
				}
			} catch (ActionForceStopException e) {
				logger.log(Level.SEVERE, e.getMessage());
				currentMenu = Builder.getInstance().getRootMenu();
			}
		}
	}

}
