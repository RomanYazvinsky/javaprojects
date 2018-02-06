package com.senla.hotel.ui;

import java.util.ArrayList;

public class Menu {
	private ArrayList<MenuItem> menuItems;

	public Menu() {
		super();
		this.menuItems = new ArrayList<>();
	}

	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
	}
	
	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}

}
