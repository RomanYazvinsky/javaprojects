package com.senla.hotel.ui;

import com.senla.hotel.api.ui.IMenu;
import com.senla.hotel.api.ui.IMenuItem;

import java.util.ArrayList;


public class Menu implements IMenu {
	private ArrayList<IMenuItem> menuItems;

	public Menu() {
		super();
		this.menuItems = new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.ui.IMenu#addMenuItem(com.senla.hotel.ui.MenuItem)
	 */
	@Override
	public void addMenuItem(IMenuItem menuItem) {
		menuItems.add(menuItem);
	}
	
	/* (non-Javadoc)
	 * @see com.senla.hotel.ui.IMenu#getMenuItems()
	 */
	@Override
	public ArrayList<IMenuItem> getMenuItems() {
		return menuItems;
	}

}
