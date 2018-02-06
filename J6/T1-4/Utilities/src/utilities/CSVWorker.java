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
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void exportClients(ArrayList<Client> clients) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.clientExportFile, false);
			fileWriter.write(Constants.clientHeaderCSV);
			for (Client client : clients) {
				fileWriter.write(System.lineSeparator() + client.toString());
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static ArrayList<Client> importClients() throws ActionForceStopException {
		ArrayList<Client> clients = new ArrayList<>();
		try {
			for (String param : Files.readAllLines(Paths.get(Constants.clientExportFile), StandardCharsets.UTF_8)) {
				if (!param.equals(Constants.clientHeaderCSV)) {
					clients.add(ParametersParser.parseClient(param));
				}
			}
			return clients;
		} catch (IOException | ArrayIndexOutOfBoundsException | IncorrectNameException
				| IncorrectParameterException e) {
			logger.log(Level.SEVERE, e.getMessage());
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
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void exportRooms(ArrayList<Room> rooms) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.roomExportFile, false);
			fileWriter.write(Constants.roomHeaderCSV);
			for (Room room : rooms) {
				fileWriter.write(System.lineSeparator() + room.toString());
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static ArrayList<Room> importRooms() throws ActionForceStopException {
		ArrayList<Room> rooms = new ArrayList<>();
		try {
			for (String param : Files.readAllLines(Paths.get(Constants.roomExportFile), StandardCharsets.UTF_8)) {
				if (!param.equals(Constants.roomHeaderCSV)) {
					rooms.add(ParametersParser.parseRoom(param));
				}
			}
			return rooms;
		} catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectParameterException e) {
			logger.log(Level.SEVERE, e.getMessage());
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
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void exportOrders(ArrayList<Order> orders) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.orderExportFile, false);
			fileWriter.write(Constants.orderHeaderCSV);
			for (Order order : orders) {
				fileWriter.write(System.lineSeparator() + order.toString());
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static ArrayList<Order> importOrders() throws ActionForceStopException {
		ArrayList<Order> orders = new ArrayList<>();
		try {
			for (String param : Files.readAllLines(Paths.get(Constants.orderExportFile), StandardCharsets.UTF_8)) {
				if (!param.equals(Constants.orderHeaderCSV)) {
					orders.add(ParametersParser.parseOrder(param));
				}
			}
			return orders;
		} catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectIDEcxeption
				| IncorrectParameterException e) {
			logger.log(Level.SEVERE, e.getMessage());
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
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void exportServices(ArrayList<Service> services) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.serviceExportFile, false);
			fileWriter.write(Constants.serviceHeaderCSV);
			for (Service service : services) {
				fileWriter.write(System.lineSeparator() + service.toString());
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static ArrayList<Service> importServices() throws ActionForceStopException {
		ArrayList<Service> services = new ArrayList<>();
		try {
			for (String param : Files.readAllLines(Paths.get(Constants.serviceExportFile), StandardCharsets.UTF_8)) {
				if (!param.equals(Constants.serviceHeaderCSV)) {
					services.add(ParametersParser.parseService(param));
				}
			}
			return services;
		} catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectIDEcxeption
				| IncorrectParameterException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ActionForceStopException();
		}
	}
}
