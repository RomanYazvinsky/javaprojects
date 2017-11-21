package utilities;

import java.util.Scanner;

public class Input {
	public static Scanner input = new Scanner(System.in);

	public static String userInput() {
		Printer.print("->");
		try {
			if (input.hasNext()) {
				return input.nextLine();
			} else {
				return "1";
			}
		} catch (IllegalStateException e) {
			LogWriter.getInstance().log(e, Input.class.getName());
			return "Error";
		}

	}
}
