package com.senla.hotel.ui;

import com.senla.hotel.facade.Hotel;
import com.senla.hotel.ui.actions.AddClientAction;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.LoadAction;
import com.senla.hotel.ui.actions.PrintRoomsAction;
import com.senla.hotel.ui.actions.SetParametersAction;
import com.senla.hotel.ui.actions.SortRoomsByCapacity;

public class Builder {
	private Menu rootMenu;
	private static Builder instance;
	
	private Builder() {
		super();
		rootMenu = new Menu("Root");
	}

	public static Builder getInstance() {
		if (instance == null) {
			instance = new Builder();
		}
		return instance;
	}
	
	public Menu getRootMenu() {
		return rootMenu;
	}

	public void buildMenu() {
		Menu addClientSubMenu = new Menu("--Adding client");
		Menu deleteClientSubMenu = new Menu("--Deleting client");
		Menu addRoomSubMenu = new Menu("--Adding room");
		Menu sortRoomsSubMenu = new Menu("--Print sorted rooms");

		MenuItem upToRoot = new MenuItem("<-Back");
		MenuItem addClient = new MenuItem("--Add Client", new AddClientAction());
		MenuItem printRooms = new MenuItem("--Print rooms", new PrintRoomsAction());
		MenuItem sortRooms = new MenuItem("--Sort rooms");
		MenuItem sortRoomsByCapacity = new MenuItem("--By capacity", new SortRoomsByCapacity());
		MenuItem setParameters = new MenuItem("--Set file paths and date", new SetParametersAction());
		MenuItem load = new MenuItem("--Load", new LoadAction());

		sortRooms.setNextMenu(sortRoomsSubMenu);

		rootMenu.addMenuItem(new MenuItem("X-Exit", new IAction() {

			@Override
			public void execute() {
				Hotel.getInstance().save();
			}

		}));
		rootMenu.addMenuItem(load);
		rootMenu.addMenuItem(printRooms);
//2		rootMenu.addMenuItem(addClient);
		rootMenu.addMenuItem(sortRooms);
		rootMenu.addMenuItem(setParameters);

		sortRoomsSubMenu.addMenuItem(upToRoot);
		sortRoomsSubMenu.addMenuItem(sortRoomsByCapacity);
	}
}
