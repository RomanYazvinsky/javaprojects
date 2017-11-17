package utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;

public class Printer {

	public static void println(String message) {
		System.out.println(message);
	}

	public static void print(String message) {
		System.out.print(message);
	}

	public static void printStringList(ArrayList<String> stringArray) {
		if (stringArray.size() == 0) {
			System.out.println("List is empty");
		} else {
			for (String message : stringArray) {
				System.out.println(message);
			}
		}
	}

	public static void printEntityList(ArrayList<? extends AEntity> entityArray) {
		if (entityArray == null) {
			System.out.println("List is empty");
		} else {
			for (AEntity entity : entityArray) {
				if (entity != null) {
					System.out.println(entity.toString());
				}
			}
		}
	}

	public static void printOrder(Order order) {
		if (order == null) {
			System.out.println("No order");
		} else {
			System.out.print(order.getID() + " " + order.getRoomID() + " " + order.getClientID() + " ");
			printDate(order.getOrderFrom());
			System.out.print(" ");
			printDate(order.getOrderTo());
			System.out.println();
		}
	}

	public static void printDate(Date date) {
		if (date == null) {
			System.out.println("Date is not setted");
		} else {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			System.out.print(calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.MONTH) + " "
					+ calendar.get(Calendar.DAY_OF_MONTH));
		}
	}

	public static void printOrders(ArrayList<Order> orders) {
		if (orders == null || orders.size() == 0) {
			System.out.println("List is empty");
		} else {
			for (Order order : orders) {
				Printer.printOrder(order);
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
			System.out.println("No room");
		} else {
			System.out.println(room);
		}
	}

	public static void printClient(Client client) {
		if (client == null) {
			System.out.println("No client");
		} else {
			System.out.println(client);
		}
	}

	public static void printService(Service service) {
		if (service == null) {
			System.out.println("No service");
		} else {
			System.out.print(service.getID() + " " + service.getName() + " " + service.getPrice() + " ");
			printDate(service.getDate());
			System.out.println();
		}
	}
}
