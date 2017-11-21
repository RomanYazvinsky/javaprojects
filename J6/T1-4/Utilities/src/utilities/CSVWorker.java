package utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

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
	public static void exportClient(Client client) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.clientDataDir + client.getID().toString() + ".csv");
			fileWriter.write(client.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			LogWriter.getInstance().log(e, "exportClient");
		}
	}

	public static Client importClient(String path) throws ActionForceStopException {
		StringBuilder params = new StringBuilder();
		try {
			Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).forEach(new Consumer<String>() {

				@Override
				public void accept(String arg0) {
					params.append(arg0);
				}
			});
			return new Client(params.toString());
		} catch (IOException | IncorrectNameException e) {
			LogWriter.getInstance().log(e, "importClient");
			throw new ActionForceStopException();
		}
	}

	public static void exportRoom(Room room) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.roomDataDir + room.getID().toString() + ".csv");
			fileWriter.write(room.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			LogWriter.getInstance().log(e, "exportRoom");
		}
	}

	public static Room importRoom(String path) throws ActionForceStopException {
		StringBuilder params = new StringBuilder();
		try {
			Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).forEach(new Consumer<String>() {

				@Override
				public void accept(String arg0) {
					params.append(arg0);
				}
			});
			Room room = new Room(params.toString());
			room.setID(Integer.parseInt(params.toString().split(",")[0]));
			return new Room(params.toString());
		} catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectParameterException e) {
			LogWriter.getInstance().log(e, "importRoom");
			throw new ActionForceStopException();
		}
	}

	public static void exportOrder(Order order) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.orderDataDir + order.getID().toString() + ".csv");
			fileWriter.write(order.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			LogWriter.getInstance().log(e, "exportOrder");
		}
	}

	public static Order importOrder(String path) throws ActionForceStopException {
		StringBuilder params = new StringBuilder();
		try {
			Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).forEach(new Consumer<String>() {

				@Override
				public void accept(String arg0) {
					params.append(arg0);
				}
			});
			Order order = new Order(params.toString());
			order.setID(Integer.parseInt(params.toString().split(",")[0]));
			return order;
		} catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectIDEcxeption e) {
			LogWriter.getInstance().log(e, "importOrder");
			throw new ActionForceStopException();
		}
	}

	public static void exportService(Service service) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.serviceDataDir + service.getID().toString() + ".csv");
			fileWriter.write(service.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			LogWriter.getInstance().log(e, "exportService");
		}
	}

	public static Service importService(String path) throws ActionForceStopException {
		StringBuilder params = new StringBuilder();
		try {
			Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).forEach(new Consumer<String>() {

				@Override
				public void accept(String arg0) {
					params.append(arg0);
				}
			});
			Service service = new Service(params.toString());
			service.setID(Integer.parseInt(params.toString().split(",")[0]));
			return service;
		} catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectIDEcxeption e) {
			LogWriter.getInstance().log(e, "importService");
			throw new ActionForceStopException();
		}
	}
}
