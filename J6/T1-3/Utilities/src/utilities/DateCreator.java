package utilities;

import java.util.Date;
import java.util.GregorianCalendar;

public class DateCreator {
	public static Date parseString(String dateString) {
		String[] dateFields = dateString.split(" ");
		try {
		return new GregorianCalendar(Integer.parseInt(dateFields[2]), Integer.parseInt(dateFields[1]) - 1,
				Integer.parseInt(dateFields[0]), 0, 0, 0).getTime();
		}catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
			LogWriter.getInstance().log(e, DateCreator.class.getName());
			return null;
		}
	}
}
