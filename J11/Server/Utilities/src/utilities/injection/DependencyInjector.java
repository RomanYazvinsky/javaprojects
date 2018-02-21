package utilities.injection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.properties.HotelProperties;

public class DependencyInjector {
	private static Logger logger = LogManager.getLogger(DependencyInjector.class);


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object newInstance(Class objectInterface) {
		String objectTypeName;
		Class objectType = null;
		try {
			objectTypeName = HotelProperties.getInstance(Constants.PATH_TO_PROPERTIES)
					.getProperty(objectInterface.getName());
			objectType = Class.forName(objectTypeName);
			return objectType.newInstance();
		} catch (EmptyObjectException | IOException | ClassNotFoundException e) {
			logger.log(Level.DEBUG, e.getMessage());
			return null;
		} catch (InstantiationException | IllegalAccessException e) {
			try {
				Method getInstance = objectType.getDeclaredMethod(HotelProperties
						.getInstance(Constants.PATH_TO_PROPERTIES).getProperty(Constants.INSTANCE_GETTER), null);
				return getInstance.invoke(objectType, null);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | EmptyObjectException | IOException e1) {
				logger.log(Level.DEBUG, e1.getMessage());
				return null;
			}
		}
	}

}
