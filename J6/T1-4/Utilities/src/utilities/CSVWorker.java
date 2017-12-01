package utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectNameException;
import com.senla.hotel.exceptions.IncorrectParameterException;

public class CSVWorker {
	private static Logger logger;
	static {
		logger = Logger.getLogger(CSVWorker.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	public static void exportClient(Client client) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.clientExportFile, true);
			fileWriter.write(client.toString() + System.lineSeparator());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "exportClient");
		}
	}

	public static ArrayList<Client> importClient() throws ActionForceStopException {
		ArrayList<Client> clients = new ArrayList<>();
		try {
			for (String param : Files.readAllLines(Paths.get(Constants.clientExportFile), StandardCharsets.UTF_8)) {
				clients.add(ParametersParser.parseClient(param));
			}
			return clients;
		} catch (IOException | ArrayIndexOutOfBoundsException | IncorrectNameException
				| IncorrectParameterException e) {
			logger.log(Level.SEVERE, "importClient");
			throw new ActionForceStopException();
		}
	}

	public static void exportRoom(Room room) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.roomExportFile, true);
			fileWriter.write(room.toString() + System.lineSeparator());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "exportRoom");
		}
	}

	public static ArrayList<Room> importRoom() throws ActionForceStopException {
		ArrayList<Room> rooms = new ArrayList<>();
		try {
			for (String param : Files.readAllLines(Paths.get(Constants.roomExportFile), StandardCharsets.UTF_8)) {
				rooms.add(ParametersParser.parseRoom(param));
			}
			return rooms;
		} catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectParameterException e) {
			logger.log(Level.SEVERE, "importRoom");
			throw new ActionForceStopException();
		}
	}

	public static void exportOrder(Order order) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.orderExportFile, true);
			fileWriter.write(order.toString() + System.lineSeparator());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "exportOrder");
		}
	}

	public static ArrayList<Order> importOrder() throws ActionForceStopException {
		ArrayList<Order> orders = new ArrayList<>();
		try {
			for (String param : Files.readAllLines(Paths.get(Constants.orderExportFile), StandardCharsets.UTF_8)) {
				orders.add(ParametersParser.parseOrder(param));
			}
			return orders;
		} catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectIDEcxeption
				| IncorrectParameterException e) {
			logger.log(Level.SEVERE, "importOrder");
			throw new ActionForceStopException();
		}
	}

	public static void exportService(Service service) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.serviceExportFile, true);
			fileWriter.write(service.toString() + System.lineSeparator());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "exportService");
		}
	}

	public static ArrayList<Service> importService() throws ActionForceStopException {
		ArrayList<Service> services = new ArrayList<>();
		try {
			for (String param : Files.readAllLines(Paths.get(Constants.serviceExportFile), StandardCharsets.UTF_8)) {
				services.add(ParametersParser.parseService(param));
			}
			return services;
		} catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectIDEcxeption
				| IncorrectParameterException e) {
			logger.log(Level.SEVERE, "importService");
			throw new ActionForceStopException();
		}
	}
}
