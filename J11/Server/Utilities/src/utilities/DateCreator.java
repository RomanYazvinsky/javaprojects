package utilities;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateCreator {
	private static Logger logger = LogManager.getLogger(DateCreator.class);


	public static Date parseString(String dateString) {
		String[] dateFields = dateString.split(" ");
		try {
			return new GregorianCalendar(Integer.parseInt(dateFields[2]), Integer.parseInt(dateFields[1]) - 1,
					Integer.parseInt(dateFields[0]), 0, 0, 0).getTime();
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			logger.log(Level.DEBUG, e.getMessage());
			return null;
		}
	}
}
