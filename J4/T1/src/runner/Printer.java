package runner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Order;

public class Printer {
	public static void print(String message) {
		System.out.println(message);
	}

	public static void printStringArray(String[] stringArray) {
		for (String message : stringArray) {
			System.out.println(message);
		}
	}

	public static void printEntityArray(AEntity[] entityArray) {
		for (AEntity entity : entityArray) {
			if (entity != null) {
				System.out.println(entity.toString());
			}
		}
	}

	public static void printOrder(Order order) {
		System.out.print(order.getID() + " " + order.getRoomID() +" " + order.getClientID() + " ");
		printDate(order.getOrderFrom());
		System.out.print(" ");
		printDate(order.getOrderTo());
		System.out.println();
	}
	
	public static void printDate(GregorianCalendar date) {
		System.out.print(date.get(Calendar.YEAR) + " " + date.get(Calendar.MONTH) + " " + date.get(Calendar.DAY_OF_MONTH));
	}
	
	public static void printOrderArray(Order[] orders) {
		for (Order order : orders) {
			Printer.printOrder(order);
		}
	}
}
