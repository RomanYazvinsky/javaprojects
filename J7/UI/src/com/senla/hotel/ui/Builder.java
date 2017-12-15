package com.senla.hotel.ui;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.addition.AddClientAction;
import com.senla.hotel.ui.actions.addition.AddOrderAction;
import com.senla.hotel.ui.actions.addition.AddOrderServiceAction;
import com.senla.hotel.ui.actions.addition.AddRoomAction;
import com.senla.hotel.ui.actions.addition.AddRoomCloneAction;
import com.senla.hotel.ui.actions.addition.AddServiceAction;
import com.senla.hotel.ui.actions.io.ClientImportAction;
import com.senla.hotel.ui.actions.io.ClientsExportAction;
import com.senla.hotel.ui.actions.io.LoadAction;
import com.senla.hotel.ui.actions.io.OrderImportAction;
import com.senla.hotel.ui.actions.io.OrdersExportAction;
import com.senla.hotel.ui.actions.io.RoomImportAction;
import com.senla.hotel.ui.actions.io.RoomsExportAction;
import com.senla.hotel.ui.actions.io.ServiceImportAction;
import com.senla.hotel.ui.actions.io.ServicesExportAction;
import com.senla.hotel.ui.actions.printers.PrintActualClientsAction;
import com.senla.hotel.ui.actions.printers.PrintClientServicesAction;
import com.senla.hotel.ui.actions.printers.PrintClientsAction;
import com.senla.hotel.ui.actions.printers.PrintFreeRoomsAction;
import com.senla.hotel.ui.actions.printers.PrintFreeRoomsInFutureAction;
import com.senla.hotel.ui.actions.printers.PrintLastRoomOrdersAction;
import com.senla.hotel.ui.actions.printers.PrintOrdersAction;
import com.senla.hotel.ui.actions.printers.PrintPriceForOrderAction;
import com.senla.hotel.ui.actions.printers.PrintRoomsAction;
import com.senla.hotel.ui.actions.printers.PrintServicesAction;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;
import com.senla.hotel.ui.actions.selectors.SelectServiceAction;
import com.senla.hotel.ui.actions.setters.SetRoomOnServiceAction;
import com.senla.hotel.ui.actions.setters.SetRoomPriceAction;
import com.senla.hotel.ui.actions.setters.SetRoomUsableAction;
import com.senla.hotel.ui.actions.setters.SetServicePriceAction;
import com.senla.hotel.ui.actions.sortprinters.SortClientsByNameAction;
import com.senla.hotel.ui.actions.sortprinters.SortFreeRoomsByCapacityAction;
import com.senla.hotel.ui.actions.sortprinters.SortFreeRoomsByPriceAction;
import com.senla.hotel.ui.actions.sortprinters.SortFreeRoomsByStarsAction;
import com.senla.hotel.ui.actions.sortprinters.SortOrdersByDateAction;
import com.senla.hotel.ui.actions.sortprinters.SortRoomsByCapacityAction;
import com.senla.hotel.ui.actions.sortprinters.SortRoomsByPriceAction;
import com.senla.hotel.ui.actions.sortprinters.SortRoomsByStarsAction;
import com.senla.hotel.ui.actions.sortprinters.SortServiceByNameAction;
import com.senla.hotel.ui.actions.sortprinters.SortServicesByPriceAction;

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
		// print & choose rooms
		Menu sortRoomsSubMenu = new Menu();
		Menu afterPrintRoomSubMenu = new Menu();
		Menu roomSubMenu = new Menu();
		Menu addRoomSubMenu = new Menu();
		// free rooms
		Menu sortFreeRoomsSubMenu = new Menu();
		Menu afterPrintFreeRoomSubMenu = new Menu();

		// print & choose clients
		Menu addClientSubMenu = new Menu();
		Menu clientSubMenu = new Menu();
		Menu sortClientSubMenu = new Menu();
		Menu afterPrintClientSubMenu = new Menu();
		Menu selectRoomToAddOrderSubMenu = new Menu();
		Menu addServiceToClientSubMenu = new Menu();


		// print & choose orders
		Menu sortOrderSubMenu = new Menu();
		Menu orderSubMenu = new Menu();
		Menu afterPrintOrderSubMenu = new Menu();
		Menu setOrderParamsSubMenu = new Menu();
		Menu selectServiceToAddSubMenu = new Menu();
		Menu addOrderSubMenu = new Menu();

		// services
		Menu sortServicesSubMenu = new Menu();
		Menu serviceSubMenu = new Menu();
		Menu afterPrintServiceSubMenu = new Menu();
		Menu addServiceSubMenu = new Menu();

		MenuItem upToRoot = new MenuItem("<-Root");
		MenuItem load = new MenuItem("--Load", new LoadAction());
		MenuItem importClient = new MenuItem("--Import client", new ClientImportAction());
		MenuItem addClientByParams = new MenuItem("--Enter params", new AddClientAction());
		MenuItem addRoomByParams = new MenuItem("--Enter params", new AddRoomAction());
		MenuItem addOrderByParams = new MenuItem("--Enter params", new AddOrderAction());
		MenuItem addServiceByParams = new MenuItem("--Enter params", new AddServiceAction());


		MenuItem addClient = new MenuItem("--Add client");
		MenuItem addRoom = new MenuItem("--Add room");
		MenuItem addService = new MenuItem("--Add service");
		MenuItem addOrder = new MenuItem("--Add order");

		MenuItem chooseRoomSortType = new MenuItem("--Print rooms");
		MenuItem chooseFreeRoomSortType = new MenuItem("--Print free rooms");
		MenuItem chooseClientSortType = new MenuItem("--Print clients");
		MenuItem chooseOrderSortType = new MenuItem("--Print orders");
		MenuItem chooseServiceSortType = new MenuItem("--Print sevices");

		MenuItem printServices = new MenuItem("--Print services", new PrintServicesAction());
		MenuItem sortServicesByPrice = new MenuItem("--By price", new SortServicesByPriceAction());
		MenuItem sortServicesByName = new MenuItem("--By name", new SortServiceByNameAction());

		MenuItem printRooms = new MenuItem("--By id", new PrintRoomsAction());
		MenuItem sortRoomsByCapacity = new MenuItem("--By capacity", new SortRoomsByCapacityAction());
		MenuItem sortRoomsByPrice = new MenuItem("--By price", new SortRoomsByPriceAction());
		MenuItem sortRoomsByStars = new MenuItem("--By stars", new SortRoomsByStarsAction());

		MenuItem printFreeRooms = new MenuItem("--By id", new PrintFreeRoomsAction());
		MenuItem sortFreeRoomsByCapacity = new MenuItem("--By capacity", new SortFreeRoomsByCapacityAction());
		MenuItem sortFreeRoomsByPrice = new MenuItem("--By price", new SortFreeRoomsByPriceAction());
		MenuItem sortFreeRoomsByStars = new MenuItem("--By stars", new SortFreeRoomsByStarsAction());
		MenuItem printFreeRoomsByDate = new MenuItem("--Print free rooms on date", new PrintFreeRoomsInFutureAction());

		MenuItem printClients = new MenuItem("--By id", new PrintClientsAction());
		MenuItem sortClientsByName = new MenuItem("--By name", new SortClientsByNameAction());
		MenuItem printActualClients = new MenuItem("--Print actual clients", new PrintActualClientsAction());

		MenuItem printOrders = new MenuItem("--By id", new PrintOrdersAction());
		MenuItem sortOrdersByDate = new MenuItem("--By date", new SortOrdersByDateAction());
		MenuItem printAvailableServices = new MenuItem("--Add service to order", new PrintServicesAction());
		MenuItem selectServiceToAdd = new MenuItem("--Select service", new SelectServiceAction());
		MenuItem addOrderService = new MenuItem("--Add service", new AddOrderServiceAction());
		MenuItem printPriceForOrder = new MenuItem("--Print price for order", new PrintPriceForOrderAction());
		MenuItem exportOrders = new MenuItem("--Export all", new OrdersExportAction());

		MenuItem setServicePrice = new MenuItem("--Set price", new SetServicePriceAction());
		MenuItem exportServices = new MenuItem("--Export", new ServicesExportAction());

		MenuItem setRoomOnService = new MenuItem("--Set room on service", new SetRoomOnServiceAction());
		MenuItem setRoomUsable = new MenuItem("--Set room usable", new SetRoomUsableAction());
		MenuItem setRoomPrice = new MenuItem("--Set room price", new SetRoomPriceAction());
		MenuItem printRoomLastOrders = new MenuItem("--Print last 3 orders", new PrintLastRoomOrdersAction());
		MenuItem addRoomClone = new MenuItem("--Add clone", new AddRoomCloneAction());
		MenuItem exportRooms = new MenuItem("--Export", new RoomsExportAction());


		MenuItem printRoomsToAddOrder = new MenuItem("--Add order", new PrintRoomsAction());
		MenuItem printClientServices = new MenuItem("--Print client's services", new PrintClientServicesAction());
		MenuItem exportClients = new MenuItem("--Export", new ClientsExportAction());

		
		MenuItem importRoom = new MenuItem("--Import room", new RoomImportAction());
		MenuItem importOrder = new MenuItem("--Import order", new OrderImportAction());
		MenuItem importService = new MenuItem("--Import service", new ServiceImportAction());

		MenuItem selectRoom = new MenuItem("--Select room by id", new SelectRoomAction());
		MenuItem selectRoomToAddOrder = new MenuItem("--Select room by id", new SelectRoomAction());
		MenuItem selectClient = new MenuItem("--Select client by id", new SelectClientAction());
		MenuItem selectOrder = new MenuItem("--Select order by id", new SelectOrderAction());
		MenuItem selectService = new MenuItem("--Select service by id", new SelectServiceAction());

		addClient.setNextMenu(addClientSubMenu);
		addOrder.setNextMenu(addOrderSubMenu);
		addService.setNextMenu(addServiceSubMenu);
		addRoom.setNextMenu(addRoomSubMenu);

		chooseRoomSortType.setNextMenu(sortRoomsSubMenu);
		printRooms.setNextMenu(afterPrintRoomSubMenu);
		sortRoomsByCapacity.setNextMenu(afterPrintRoomSubMenu);
		sortRoomsByPrice.setNextMenu(afterPrintRoomSubMenu);
		sortRoomsByStars.setNextMenu(afterPrintRoomSubMenu);

		chooseFreeRoomSortType.setNextMenu(sortFreeRoomsSubMenu);
		printFreeRooms.setNextMenu(afterPrintFreeRoomSubMenu);
		sortFreeRoomsByCapacity.setNextMenu(afterPrintFreeRoomSubMenu);
		sortFreeRoomsByPrice.setNextMenu(afterPrintFreeRoomSubMenu);
		sortFreeRoomsByStars.setNextMenu(afterPrintFreeRoomSubMenu);
		printFreeRoomsByDate.setNextMenu(afterPrintFreeRoomSubMenu);

		selectRoom.setNextMenu(roomSubMenu);
		selectRoomToAddOrder.setNextMenu(setOrderParamsSubMenu);

		chooseClientSortType.setNextMenu(sortClientSubMenu);
		printClients.setNextMenu(afterPrintClientSubMenu);
		printActualClients.setNextMenu(afterPrintClientSubMenu);
		sortClientsByName.setNextMenu(afterPrintClientSubMenu);
		selectClient.setNextMenu(clientSubMenu);
		printRoomsToAddOrder.setNextMenu(selectRoomToAddOrderSubMenu);

		chooseOrderSortType.setNextMenu(sortOrderSubMenu);
		printOrders.setNextMenu(afterPrintOrderSubMenu);
		sortOrdersByDate.setNextMenu(afterPrintOrderSubMenu);
		selectOrder.setNextMenu(orderSubMenu);
		printAvailableServices.setNextMenu(selectServiceToAddSubMenu);
		selectServiceToAdd.setNextMenu(addServiceToClientSubMenu);

		chooseServiceSortType.setNextMenu(sortServicesSubMenu);
		printServices.setNextMenu(afterPrintServiceSubMenu);
		sortServicesByName.setNextMenu(afterPrintServiceSubMenu);
		sortServicesByPrice.setNextMenu(afterPrintServiceSubMenu);
		selectService.setNextMenu(serviceSubMenu);

		rootMenu.addMenuItem(new MenuItem("<<-Exit", new IAction() {

			@Override
			public void execute() {
				Facade.getInstance().save();
			}

		}));
		rootMenu.addMenuItem(load);
		rootMenu.addMenuItem(chooseRoomSortType);
		rootMenu.addMenuItem(chooseFreeRoomSortType);
		rootMenu.addMenuItem(chooseClientSortType);
		rootMenu.addMenuItem(chooseOrderSortType);
		rootMenu.addMenuItem(chooseServiceSortType);
		rootMenu.addMenuItem(addClient);
		rootMenu.addMenuItem(addRoom);
		rootMenu.addMenuItem(addService);
		rootMenu.addMenuItem(importOrder);

		addClientSubMenu.addMenuItem(upToRoot);
		addClientSubMenu.addMenuItem(importClient);
		addClientSubMenu.addMenuItem(addClientByParams);
		
		addRoomSubMenu.addMenuItem(upToRoot);
		addRoomSubMenu.addMenuItem(importRoom);
		addRoomSubMenu.addMenuItem(addRoomByParams);
		
		addOrderSubMenu.addMenuItem(upToRoot);
		addOrderSubMenu.addMenuItem(importOrder);
		addOrderSubMenu.addMenuItem(addOrderByParams);
		
		addServiceSubMenu.addMenuItem(upToRoot);
		addServiceSubMenu.addMenuItem(importService);
		addServiceSubMenu.addMenuItem(addServiceByParams);

		setOrderParamsSubMenu.addMenuItem(upToRoot);
		setOrderParamsSubMenu.addMenuItem(addOrder);

		selectRoomToAddOrderSubMenu.addMenuItem(upToRoot);
		selectRoomToAddOrderSubMenu.addMenuItem(selectRoomToAddOrder);

		clientSubMenu.addMenuItem(upToRoot);
		clientSubMenu.addMenuItem(printRoomsToAddOrder);
		clientSubMenu.addMenuItem(printClientServices);

		afterPrintRoomSubMenu.addMenuItem(upToRoot);
		afterPrintRoomSubMenu.addMenuItem(selectRoom);
		afterPrintRoomSubMenu.addMenuItem(exportRooms);

		afterPrintFreeRoomSubMenu.addMenuItem(upToRoot);
		afterPrintFreeRoomSubMenu.addMenuItem(selectRoom);

		roomSubMenu.addMenuItem(upToRoot);
		roomSubMenu.addMenuItem(setRoomOnService);
		roomSubMenu.addMenuItem(setRoomUsable);
		roomSubMenu.addMenuItem(setRoomPrice);
		roomSubMenu.addMenuItem(printRoomLastOrders);
		roomSubMenu.addMenuItem(addRoomClone);

		sortRoomsSubMenu.addMenuItem(upToRoot);
		sortRoomsSubMenu.addMenuItem(printRooms);
		sortRoomsSubMenu.addMenuItem(sortRoomsByCapacity);
		sortRoomsSubMenu.addMenuItem(sortRoomsByPrice);
		sortRoomsSubMenu.addMenuItem(sortRoomsByStars);

		sortFreeRoomsSubMenu.addMenuItem(upToRoot);
		sortFreeRoomsSubMenu.addMenuItem(printFreeRooms);
		sortFreeRoomsSubMenu.addMenuItem(sortFreeRoomsByCapacity);
		sortFreeRoomsSubMenu.addMenuItem(sortFreeRoomsByPrice);
		sortFreeRoomsSubMenu.addMenuItem(sortFreeRoomsByStars);
		sortFreeRoomsSubMenu.addMenuItem(printFreeRoomsByDate);

		sortClientSubMenu.addMenuItem(upToRoot);
		sortClientSubMenu.addMenuItem(printClients);
		sortClientSubMenu.addMenuItem(sortClientsByName);
		sortClientSubMenu.addMenuItem(printActualClients);

		afterPrintClientSubMenu.addMenuItem(upToRoot);
		afterPrintClientSubMenu.addMenuItem(selectClient);
		afterPrintClientSubMenu.addMenuItem(exportClients);

		sortOrderSubMenu.addMenuItem(upToRoot);
		sortOrderSubMenu.addMenuItem(printOrders);
		sortOrderSubMenu.addMenuItem(sortOrdersByDate);

		afterPrintOrderSubMenu.addMenuItem(upToRoot);
		afterPrintOrderSubMenu.addMenuItem(selectOrder);
		afterPrintOrderSubMenu.addMenuItem(exportOrders);

		orderSubMenu.addMenuItem(upToRoot);
		orderSubMenu.addMenuItem(printAvailableServices);
		orderSubMenu.addMenuItem(printPriceForOrder);

		selectServiceToAddSubMenu.addMenuItem(upToRoot);
		selectServiceToAddSubMenu.addMenuItem(selectServiceToAdd);

		addServiceToClientSubMenu.addMenuItem(upToRoot);
		addServiceToClientSubMenu.addMenuItem(addOrderService);

		sortServicesSubMenu.addMenuItem(upToRoot);
		sortServicesSubMenu.addMenuItem(printServices);
		sortServicesSubMenu.addMenuItem(sortServicesByPrice);
		sortServicesSubMenu.addMenuItem(sortServicesByName);

		afterPrintServiceSubMenu.addMenuItem(upToRoot);
		afterPrintServiceSubMenu.addMenuItem(selectService);
		afterPrintServiceSubMenu.addMenuItem(exportServices);

		serviceSubMenu.addMenuItem(upToRoot);
		serviceSubMenu.addMenuItem(setServicePrice);

	}
}
