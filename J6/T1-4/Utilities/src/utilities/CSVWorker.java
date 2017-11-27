package utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
			FileWriter fileWriter = new FileWriter(Constants.getPath(client));
			fileWriter.write(Constants.clientHeaderCSV + client.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			LogWriter.getInstance().log(e, "exportClient");
		}
	}

	public static Client importClient(String path) throws ActionForceStopException {
		ArrayList<String> params = new ArrayList<>();
		try {
			Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).forEach(new Consumer<String>() {

				@Override
				public void accept(String arg0) {
					params.add(arg0);
				}
			});
			Client client = new Client(params.get(1));
			client.setID(Integer.parseInt(params.get(1).split(",")[0]));
			return client;
		} catch (IOException | IncorrectNameException | ArrayIndexOutOfBoundsException e) {
			LogWriter.getInstance().log(e, "importClient");
			throw new ActionForceStopException();
		}
	}

	public static void exportRoom(Room room) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.getPath(room));
			fileWriter.write(Constants.roomHeaderCSV + room.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			LogWriter.getInstance().log(e, "exportRoom");
		}
	}

	public static Room importRoom(String path) throws ActionForceStopException {
		ArrayList<String> params = new ArrayList<>();
		try {
			Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).forEach(new Consumer<String>() {

				@Override
				public void accept(String arg0) {
					params.add(arg0);
				}
			});
			String data = params.get(1);
			Room room = new Room(data);
			room.setID(Integer.parseInt(data.split(",")[0]));
			return room;
		} catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectParameterException e) {
			LogWriter.getInstance().log(e, "importRoom");
			throw new ActionForceStopException();
		}
	}

	public static void exportOrder(Order order) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.getPath(order));
			fileWriter.write(Constants.orderHeaderCSV + order.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			LogWriter.getInstance().log(e, "exportOrder");
		}
	}

	public static Order importOrder(String path) throws ActionForceStopException {
		ArrayList<String> params = new ArrayList<>();
		try {
			Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).forEach(new Consumer<String>() {

				@Override
				public void accept(String arg0) {
					params.add(arg0);
				}
			});
			String data = params.get(1);
			Order order = new Order(data);
			order.setID(Integer.parseInt(data.split(",")[0]));
			return order;
		} catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException | IncorrectIDEcxeption e) {
			LogWriter.getInstance().log(e, "importOrder");
			throw new ActionForceStopException();
		}
	}

	public static void exportService(Service service) {
		try {
			FileWriter fileWriter = new FileWriter(Constants.getPath(service));
			fileWriter.write(Constants.serviceHeaderCSV + service.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			LogWriter.getInstance().log(e, "exportService");
		}
	}

	public static Service importService(String path) throws ActionForceStopException {
		ArrayList<String> params = new ArrayList<>();
		try {
			Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).forEach(new Consumer<String>() {

				@Override
				public void accept(String arg0) {
					params.add(arg0);
				}
			});
			String data = params.get(1);
			Service service = new Service(data);
			service.setID(Integer.parseInt(data.split(",")[0]));
			return service;
		} catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectIDEcxeption e) {
			LogWriter.getInstance().log(e, "importService");
			throw new ActionForceStopException();
		}
	}
}
