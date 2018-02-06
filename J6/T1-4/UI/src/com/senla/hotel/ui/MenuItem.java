package com.senla.hotel.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.NoAction;

public class MenuItem {
	private static Logger logger;
	private String title;
	private IAction action;
	private Menu nextMenu;

	static {
		logger = Logger.getLogger(MenuItem.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	public MenuItem(String title) {
		super();
		this.title = title;
		this.action = new NoAction();
		nextMenu = Builder.getInstance().getRootMenu();
	}

	public MenuItem(String title, IAction action) {
		super();
		this.title = title;
		this.action = action;
		nextMenu = Builder.getInstance().getRootMenu();
	}

	public void setNextMenu(Menu menu) {
		nextMenu = menu;
	}

	public void doAction() throws ActionForceStopException {
		try {
			action.execute();
		} catch (ActionForceStopException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public String getTitle() {
		return title;
	}

	public IAction getAction() {
		return action;
	}

	public Menu getNextMenu() {
		return nextMenu;
	}

}
