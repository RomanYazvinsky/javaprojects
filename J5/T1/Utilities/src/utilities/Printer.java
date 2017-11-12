package utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;

public class Printer {
	private static final String ASK_FOR_ORDER_FILE = "Enter the orders file: ";
	private static final String ASK_FOR_CLIENT_FILE = "Enter the clients file: ";
	private static final String ASK_FOR_SERVICE_FILE = "Enter the services file: ";
	private static final String ASK_FOR_ROOM_FILE = "Enter the rooms file: ";
	private static final String ASK_FOR_DATE = "Enter the current date (d m y): ";

	public static void askForOrderFile() {
		println(ASK_FOR_ORDER_FILE);
	}

	public static void askForClientFile() {
		println(ASK_FOR_CLIENT_FILE);
	}

	public static void askForServiceFile() {
		println(ASK_FOR_SERVICE_FILE);
	}

	public static void askForRoomFile() {
		println(ASK_FOR_ROOM_FILE);
	}
	
	public static void askForDate() {
		println(ASK_FOR_DATE);
	}

	public static void println(String message) {
		System.out.println(message);
	}
	
	public static void print(String message) {
		System.out.print(message);
	}

	public static void printStringList(ArrayList<String> stringArray) {
		if (stringArray .size() == 0) {
			System.out.println("Array is empty");
		} else {
			for (String message : stringArray) {
				System.out.println(message);
			}
		}
	}

	public static void printEntityArray(AEntity[] entityArray) {
		if (entityArray == null) {
			System.out.println("Array is empty");
		} else {
			for (AEntity entity : entityArray) {
				if (entity != null) {
					System.out.println(entity.toString());
				}
			}
		}
	}
	
	public static void printEntityList(ArrayList<? extends AEntity> entityArray) {
		if (entityArray == null) {
			System.out.println("Array is empty");
		} else {
			for (AEntity entity : entityArray) {
				if (entity != null) {
					System.out.println(entity.toString());
				}
			}
		}
	}

	public static void printOrder(Order order) {
		if (order != null) {
			System.out.print(order.getID() + " " + order.getRoomID() + " " + order.getClientID() + " ");
			printDate(order.getOrderFrom());
			System.out.print(" ");
			printDate(order.getOrderTo());
			System.out.println();
		} else {
			System.out.println("Order does not exist");
		}
	}

	public static void printDate(GregorianCalendar date) {
		if (date != null) {
			System.out.print(
					date.get(Calendar.YEAR) + " " + date.get(Calendar.MONTH) + " " + date.get(Calendar.DAY_OF_MONTH));
		} else {
			System.out.println("date does not exist");
		}
	}

	public static void printOrderArray(Order[] orders) {
		if (orders == null) {
			System.out.println("Array is empty");
		} else {
			for (Order order : orders) {
				Printer.printOrder(order);
			}
		}
	}

	public static void printServiceArray(Service[] services) {
		if (services == null) {
			System.out.println("Array is empty");
		} else {
			for (Service service : services) {
				Printer.printService(service);
			}
		}
	}

	public static void isSuccessful(Boolean result) {
		if (result) {
			System.out.println("Successfull");
		} else {
			System.out.println("Error");
		}
	}

	public static void printRoom(Room room) {
		if (room == null) {
			System.out.println("This room does not exist");
		} else {
			System.out.println(room);
		}
	}

	public static void printClient(Client client) {
		if (client == null) {
			System.out.println("This client does not exist");
		} else {
			System.out.println(client);
		}
	}

	public static void printService(Service service) {
		if (service == null) {
			System.out.println("This service does not exist");
		} else {
			System.out.print(service.getID() + " " + service.getName() + " " + service.getPrice() + " ");
			printDate(service.getDate());
			System.out.println();
		}
	}
}
