package com.senla.hotel.ui;

import java.util.ArrayList;

public class Menu {
	private String name;
	private ArrayList<MenuItem> menuItems;

	public Menu(String name) {
		super();
		this.name = name;
		this.menuItems = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
	}
	
	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}

}
