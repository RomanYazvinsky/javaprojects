package utilities;

import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Input {
	private static Logger logger = LogManager.getLogger(Input.class);


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
			logger.log(Level.DEBUG,  e.getMessage());
			throw e;
		}

	}
}
