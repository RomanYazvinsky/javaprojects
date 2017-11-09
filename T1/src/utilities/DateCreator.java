package utilities;

import java.util.GregorianCalendar;

public class DateCreator {
	public static GregorianCalendar parseString(String dateString) {
		String[] dateFields = dateString.split(" ");
		if (dateFields.length != 3) {
			return null;
		}
		return new GregorianCalendar(Integer.parseInt(dateFields[2]), Integer.parseInt(dateFields[1]),
				Integer.parseInt(dateFields[0]), 0, 0, 0);
	}
}
