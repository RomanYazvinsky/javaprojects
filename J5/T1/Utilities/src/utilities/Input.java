package utilities;

import java.util.Scanner;

public class Input {
	public static Scanner input = new Scanner(System.in);
	public static String userInput() {
		Printer.print("->");
		if (input.hasNext()) {
			return input.nextLine();
		}else {
			return "1";
		}
	}
}
