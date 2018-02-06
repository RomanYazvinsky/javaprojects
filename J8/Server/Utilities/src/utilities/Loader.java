package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.IEntity;

public class Loader {
	private static Logger logger;
	static {
		logger = Logger.getLogger(Loader.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<? extends IEntity > load(String path) throws IOException, ClassNotFoundException {
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			ObjectInputStream objectInputStream;
			objectInputStream = new ObjectInputStream(fileInputStream);
			return (ArrayList<? extends IEntity>) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}
	

}
