package com.senla.hotel.api.ui;

import java.util.ArrayList;

public interface IMenu {

	void addMenuItem(IMenuItem menuItem);

	ArrayList<IMenuItem> getMenuItems();

}