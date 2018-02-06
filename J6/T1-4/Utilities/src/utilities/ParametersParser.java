package utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectNameException;
import com.senla.hotel.exceptions.IncorrectParameterException;

public class ParametersParser {
	private static Logger logger;
	static {
		logger = Logger.getLogger(ParametersParser.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	public static Client parseClient(String data)
			throws IncorrectNameException, ArrayIndexOutOfBoundsException, IncorrectParameterException {
		try {
			Integer id;
			String name;
			if (data == null || data.isEmpty()) {
				throw new IncorrectParameterException();
			}
			String[] params = data.split(",");
			if (params.length != 2) {
				throw new IncorrectParameterException();
			}
			id = Integer.parseInt(params[0].trim());
			name = params[1].trim();
			Client client = new Client(name);
			client.setId(id);
			return client;
		} catch (IncorrectNameException | ArrayIndexOutOfBoundsException | IncorrectParameterException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public static Room parseRoom(String data)
			throws ArrayIndexOutOfBoundsException, NumberFormatException, IncorrectParameterException {
		try {
			String[] roomData = data.split(",");
			if (roomData.length < 4) {
				throw new IncorrectParameterException();
			}
			Integer id = Integer.parseInt(roomData[0].trim());
			HashSet<Integer> clientIds = new HashSet<>();
			Integer capacity = Integer.parseInt(roomData[1].trim());
			Integer star = Integer.parseInt(roomData[2].trim());
			RoomStatus status = RoomStatus.valueOf(roomData[3].trim());
			Integer pricePerDay = Integer.parseInt(roomData[4].trim());
			if (pricePerDay <= 0 || roomData.length < 5) {
				throw new IncorrectParameterException();
			}
			for (int i = 0; i < roomData.length - 5 && i < capacity; i++) {
				if (roomData[i + 5] != null) {
					clientIds.add(Integer.parseInt(roomData[i + 5].trim()));
				}
			}
			Room room = new Room(capacity, star, status, pricePerDay);
			room.setId(id);
			return room;
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectParameterException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public static Order parseOrder(String data) throws ArrayIndexOutOfBoundsException, NumberFormatException,
			IncorrectIDEcxeption, IncorrectParameterException {
		try {
			if (data == null || data.isEmpty()) {
				throw new IncorrectParameterException();
			}
			ArrayList<Service> services = new ArrayList<>();
			Integer id, roomId, clientId;
			Date orderFrom, orderTo;
			String[] orderData = data.split(",");
			if (orderData.length < 5) {
				throw new IncorrectParameterException();
			}
			services = new ArrayList<Service>();
			id = Integer.parseInt(orderData[0].trim());
			roomId = Integer.parseInt(orderData[1].trim());
			clientId = Integer.parseInt(orderData[2].trim());
			orderFrom = new Date();
			orderFrom.setTime(Long.parseLong(orderData[3].trim()));
			orderTo = new Date();
			orderTo.setTime(Long.parseLong(orderData[4].trim()));
			for (int i = 0; i < orderData.length - 5;) {
				services.add(parseService(orderData[i++ + 5] + ", " + orderData[i++ + 5] + ", " + orderData[i++ + 5]
						+ ", " + orderData[i++ + 5]));
			}
			Order order = new Order(roomId, clientId, orderFrom, orderTo, services);
			order.setId(id);
			return order;
		} catch (NumberFormatException | IncorrectIDEcxeption | ArrayIndexOutOfBoundsException
				| IncorrectParameterException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public static Service parseService(String data) throws NumberFormatException, ArrayIndexOutOfBoundsException,
			IncorrectIDEcxeption, IncorrectParameterException {
		try {
			String[] serviceData = data.split(",");
			if (serviceData.length != 4) {
				throw new IncorrectParameterException();
			}
			Integer id = Integer.parseInt(serviceData[0].trim());
			Integer price = Integer.parseInt(serviceData[1].trim());
			String name = serviceData[2].trim();
			Date date = new Date();
			date.setTime(Long.parseLong(serviceData[3].trim()));
			Service service = new Service(price, name, date);
			service.setId(id);
			return service;
		} catch (NumberFormatException | IncorrectIDEcxeption | ArrayIndexOutOfBoundsException
				| IncorrectParameterException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

}
