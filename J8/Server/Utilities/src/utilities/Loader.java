package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;

public class Loader {
	private static Logger logger;
	static {
		logger = Logger.getLogger(Loader.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Client> loadClients(String path) throws IOException, ClassNotFoundException {
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			ObjectInputStream objectInputStream;
			objectInputStream = new ObjectInputStream(fileInputStream);
			return (ArrayList<Client>) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Room> loadRooms(String path) throws IOException, ClassNotFoundException {
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			ObjectInputStream objectInputStream;
			objectInputStream = new ObjectInputStream(fileInputStream);
			return (ArrayList<Room>) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Order> loadOrders(String path) throws IOException, ClassNotFoundException {
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			ObjectInputStream objectInputStream;
			objectInputStream = new ObjectInputStream(fileInputStream);
			return (ArrayList<Order>) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Service> loadServices(String path) throws IOException, ClassNotFoundException {
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			ObjectInputStream objectInputStream;
			objectInputStream = new ObjectInputStream(fileInputStream);
			return (ArrayList<Service>) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}
}
