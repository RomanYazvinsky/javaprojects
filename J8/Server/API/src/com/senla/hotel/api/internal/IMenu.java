package com.senla.hotel.api.internal;

import java.util.ArrayList;

public interface IMenu {

	void addMenuItem(IMenuItem menuItem);

	ArrayList<IMenuItem> getMenuItems();

}