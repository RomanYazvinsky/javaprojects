package utilities;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;

public class Input {
	private static Logger logger;
	static {
		logger = Logger.getLogger(Input.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

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
			logger.log(Level.SEVERE,  e.getMessage());
			return "Error";
		}

	}
}
