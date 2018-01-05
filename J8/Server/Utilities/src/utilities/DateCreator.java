package utilities;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;

public class DateCreator {
	private static Logger logger;
	static {
		logger = Logger.getLogger(DateCreator.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	public static Date parseString(String dateString) {
		String[] dateFields = dateString.split(" ");
		try {
			return new GregorianCalendar(Integer.parseInt(dateFields[2]), Integer.parseInt(dateFields[1]) - 1,
					Integer.parseInt(dateFields[0]), 0, 0, 0).getTime();
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}
}
