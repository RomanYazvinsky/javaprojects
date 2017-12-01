package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;

public class Saver {
	private static Logger logger;
	static {
		logger = Logger.getLogger(Saver.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	public static void saveClients(String path, ArrayList<Client> clients) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(clients);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "saveClients");
			throw e;
		}
	}

	public static void saveOrders(String path, ArrayList<Order> orders) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(orders);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "saveOrders");
			throw e;
		}
	}

	public static void saveRooms(String path, ArrayList<Room> rooms) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(rooms);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "saveRooms");
			throw e;
		}
	}

	public static void saveServices(String path, ArrayList<Service> services) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(services);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "saveServices");
			throw e;
		}
	}
}
