package com.senla.hotel.ui;

import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.NoAction;

public class MenuItem {
	private String title;
	private IAction action;
	private Menu nextMenu;

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
	
	public void doAction() {
		action.execute();
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
