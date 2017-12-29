package utilities;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.properties.HotelProperties;

public class DependencyInjector {
	private static Logger logger;
	static {
		logger = Logger.getLogger(DateCreator.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	public static Object newInstance(Class objectInterface) {
		String objectTypeName;
		try {
			objectTypeName = HotelProperties.getInstance(Constants.PATH_TO_PROPERTIES)
					.getProperty(objectInterface.getName());
			Class objectType = Class.forName(objectTypeName);
			return objectType.newInstance();
		} catch (EmptyObjectException | IOException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			logger.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}
}
