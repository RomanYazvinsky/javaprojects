package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.IEntity;

public class Saver {
	private static Logger logger;
	static {
		logger = Logger.getLogger(Saver.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}
	
	public static void save(String path, ArrayList<? extends IEntity> clients) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(clients);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}
}
