package com.senla.hotel.ui;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.LoadAction;
import com.senla.hotel.ui.actions.addition.AddClientAction;
import com.senla.hotel.ui.actions.addition.AddOrderAction;
import com.senla.hotel.ui.actions.addition.AddOrderServiceAction;
import com.senla.hotel.ui.actions.addition.AddRoomAction;
import com.senla.hotel.ui.actions.addition.AddServiceAction;
import com.senla.hotel.ui.actions.printers.PrintClientServicesAction;
import com.senla.hotel.ui.actions.printers.PrintClientsAction;
import com.senla.hotel.ui.actions.printers.PrintLastThreeOrdersAction;
import com.senla.hotel.ui.actions.printers.PrintOrdersAction;
import com.senla.hotel.ui.actions.printers.PrintRoomsAction;
import com.senla.hotel.ui.actions.printers.PrintServicesAction;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;
import com.senla.hotel.ui.actions.selectors.SelectServiceAction;
import com.senla.hotel.ui.actions.setters.SetFilePathsAction;
import com.senla.hotel.ui.actions.setters.SetRoomOnServiceAction;
import com.senla.hotel.ui.actions.setters.SetRoomPriceAction;
import com.senla.hotel.ui.actions.setters.SetRoomUsableAction;
import com.senla.hotel.ui.actions.sortprinters.SortClientsByNameAction;
import com.senla.hotel.ui.actions.sortprinters.SortOrdersByDateAction;
import com.senla.hotel.ui.actions.sortprinters.SortRoomsByCapacityAction;
import com.senla.hotel.ui.actions.sortprinters.SortRoomsByPriceAction;
import com.senla.hotel.ui.actions.sortprinters.SortRoomsByStarsAction;

public class Builder {
	private Menu rootMenu;
	private static Builder instance;

	private Builder() {
		super();
		rootMenu = new Menu();
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
		//print & choose rooms
		Menu sortRoomsSubMenu = new Menu();
		Menu afterPrintRoomSubMenu = new Menu();
		Menu roomSubMenu = new Menu();

		//print & choose clients
		Menu clientSubMenu = new Menu();
		Menu sortClientSubMenu = new Menu();
		Menu afterPrintClientSubMenu = new Menu();
		Menu selectRoomToAddOrderSubMenu = new Menu();

		
		//print & choose orders
		Menu sortOrderSubMenu = new Menu();
		Menu orderSubMenu = new Menu();
		Menu afterPrintOrderSubMenu = new Menu();
		Menu setOrderParamsSubMenu = new Menu();
		Menu selectServiceToAddSubMenu = new Menu();
		Menu addServiceSubMenu = new Menu();

		MenuItem upToRoot = new MenuItem("<-Root");
		MenuItem load = new MenuItem("--Load", new LoadAction());
		MenuItem setParameters = new MenuItem("--Set file paths", new SetFilePathsAction());

		
		MenuItem addClient = new MenuItem("--Add client", new AddClientAction());
		MenuItem addRoom = new MenuItem("--Add room", new AddRoomAction());
		MenuItem addService = new MenuItem("--Add service", new AddServiceAction());
		MenuItem addOrder = new MenuItem("--Add order", new AddOrderAction());

		MenuItem chooseRoomSortType = new MenuItem("--Print rooms");
		MenuItem chooseClientSortType = new MenuItem("--Print clients");
		MenuItem chooseOrderSortType = new MenuItem("--Print orders");

		MenuItem printServices = new MenuItem("--Print services", new PrintServicesAction());

		MenuItem printRooms = new MenuItem("--By id", new PrintRoomsAction());
		MenuItem sortRoomsByCapacity = new MenuItem("--By capacity", new SortRoomsByCapacityAction());
		MenuItem sortRoomsByPrice = new MenuItem("--By price", new SortRoomsByPriceAction());
		MenuItem sortRoomsByStars = new MenuItem("--By stars", new SortRoomsByStarsAction());
		
		MenuItem printClients = new MenuItem("--By id", new PrintClientsAction());
		MenuItem sortClientsByName = new MenuItem("--By name", new SortClientsByNameAction());
		
		MenuItem printOrders = new MenuItem("--By id", new PrintOrdersAction());
		MenuItem sortOrdersByDate = new MenuItem("--By date", new SortOrdersByDateAction());
		MenuItem printAvailableServices = new MenuItem("--Add Service", new PrintServicesAction());
		MenuItem selectServiceToAdd  = new MenuItem("--Select service", new SelectServiceAction());
		MenuItem addOrderService = new MenuItem("--Add service", new AddOrderServiceAction());
		
		MenuItem setRoomOnService = new MenuItem("--Set room on service", new SetRoomOnServiceAction());
		MenuItem setRoomUsable = new MenuItem("--Set room usable", new SetRoomUsableAction());
		MenuItem setRoomPrice = new MenuItem("--Set room price", new SetRoomPriceAction());
		MenuItem printRoomLastOrders = new MenuItem("--Print last 3 orders", new PrintLastThreeOrdersAction());

		MenuItem printRoomsToAddOrder = new MenuItem("--Add order", new PrintRoomsAction());
		MenuItem printClientServices = new MenuItem("--Print client's services", new PrintClientServicesAction());
		
		MenuItem selectRoom = new MenuItem("--Select room", new SelectRoomAction());
		MenuItem selectRoomToAddOrder = new MenuItem("--Select room", new SelectRoomAction());
		MenuItem selectClient = new MenuItem("--Select client", new SelectClientAction());
		MenuItem selectOrder = new MenuItem("--Select order", new SelectOrderAction());

		
		
		chooseRoomSortType.setNextMenu(sortRoomsSubMenu);
		printRooms.setNextMenu(afterPrintRoomSubMenu);
		sortRoomsByCapacity.setNextMenu(afterPrintRoomSubMenu);
		sortRoomsByPrice.setNextMenu(afterPrintRoomSubMenu);
		sortRoomsByStars.setNextMenu(afterPrintRoomSubMenu);

		selectRoom.setNextMenu(roomSubMenu);
		selectRoomToAddOrder.setNextMenu(setOrderParamsSubMenu);
		
		chooseClientSortType.setNextMenu(sortClientSubMenu);
		printClients.setNextMenu(afterPrintClientSubMenu);
		sortClientsByName.setNextMenu(afterPrintClientSubMenu);
		selectClient.setNextMenu(clientSubMenu);
		printRoomsToAddOrder.setNextMenu(selectRoomToAddOrderSubMenu);

		chooseOrderSortType.setNextMenu(sortOrderSubMenu);
		printOrders.setNextMenu(afterPrintOrderSubMenu);
		sortOrdersByDate.setNextMenu(afterPrintOrderSubMenu);
		selectOrder.setNextMenu(orderSubMenu);
		printAvailableServices.setNextMenu(selectServiceToAddSubMenu);
		selectServiceToAdd.setNextMenu(addServiceSubMenu);
		
		
		rootMenu.addMenuItem(new MenuItem("<<-Exit", new IAction() {

			@Override
			public void execute() {
				Facade.getInstance().save();
			}

		}));
		rootMenu.addMenuItem(load);
		rootMenu.addMenuItem(chooseRoomSortType);
		rootMenu.addMenuItem(chooseClientSortType);
		rootMenu.addMenuItem(chooseOrderSortType);
		rootMenu.addMenuItem(printServices);
		rootMenu.addMenuItem(addClient);
		rootMenu.addMenuItem(addRoom);
		rootMenu.addMenuItem(addOrder);
		rootMenu.addMenuItem(addService);
		rootMenu.addMenuItem(setParameters);

		setOrderParamsSubMenu.addMenuItem(upToRoot);
		setOrderParamsSubMenu.addMenuItem(addOrder);

		selectRoomToAddOrderSubMenu.addMenuItem(upToRoot);
		selectRoomToAddOrderSubMenu.addMenuItem(selectRoomToAddOrder);

		clientSubMenu.addMenuItem(upToRoot);
		clientSubMenu.addMenuItem(printRoomsToAddOrder);
		clientSubMenu.addMenuItem(printClientServices);

		afterPrintRoomSubMenu.addMenuItem(upToRoot);
		afterPrintRoomSubMenu.addMenuItem(selectRoom);

		roomSubMenu.addMenuItem(upToRoot);
		roomSubMenu.addMenuItem(setRoomOnService);
		roomSubMenu.addMenuItem(setRoomUsable);
		roomSubMenu.addMenuItem(setRoomPrice);
		roomSubMenu.addMenuItem(printRoomLastOrders);


		sortRoomsSubMenu.addMenuItem(upToRoot);
		sortRoomsSubMenu.addMenuItem(printRooms);
		sortRoomsSubMenu.addMenuItem(sortRoomsByCapacity);
		sortRoomsSubMenu.addMenuItem(sortRoomsByPrice);
		sortRoomsSubMenu.addMenuItem(sortRoomsByStars);
		
		sortClientSubMenu.addMenuItem(upToRoot);
		sortClientSubMenu.addMenuItem(printClients);
		sortClientSubMenu.addMenuItem(sortClientsByName);
		
		afterPrintClientSubMenu.addMenuItem(upToRoot);
		afterPrintClientSubMenu.addMenuItem(selectClient);
		
		sortOrderSubMenu.addMenuItem(upToRoot);
		sortOrderSubMenu.addMenuItem(printOrders);
		sortOrderSubMenu.addMenuItem(sortOrdersByDate);
		
		afterPrintOrderSubMenu.addMenuItem(upToRoot);
		afterPrintOrderSubMenu.addMenuItem(selectOrder);
		
		orderSubMenu.addMenuItem(upToRoot);
		orderSubMenu.addMenuItem(printAvailableServices);
		
		selectServiceToAddSubMenu.addMenuItem(upToRoot);
		selectServiceToAddSubMenu.addMenuItem(selectServiceToAdd);
		
		addServiceSubMenu.addMenuItem(upToRoot);
		addServiceSubMenu.addMenuItem(addOrderService);
	}
}
