package utilities;

import static com.senla.hotel.constants.EntityColumnOrder.ID;
import static com.senla.hotel.constants.EntityColumnOrder.NAME;
import static com.senla.hotel.constants.EntityColumnOrder.ORDER_CLIENT;
import static com.senla.hotel.constants.EntityColumnOrder.ORDER_FROM;
import static com.senla.hotel.constants.EntityColumnOrder.ORDER_ROOM;
import static com.senla.hotel.constants.EntityColumnOrder.ORDER_TO;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_CAPACITY;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_NUMBER;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_PRICE;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_STAR;
import static com.senla.hotel.constants.EntityColumnOrder.ROOM_STATUS;
import static com.senla.hotel.constants.EntityColumnOrder.SERVICE_DATE;
import static com.senla.hotel.constants.EntityColumnOrder.SERVICE_PRICE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectNameException;
import com.senla.hotel.exceptions.IncorrectParameterException;

public class EntityParser {
	private static Logger logger = LogManager.getLogger(EntityParser.class);

	@SuppressWarnings("rawtypes")
	public static Object parse(Class entityClass, String data) throws Exception {
		try {
			if (entityClass.equals(Client.class)) {
				return parseClient(data);
			}
			if (entityClass.equals(Order.class)) {
				return parseOrder(data);
			}
			if (entityClass.equals(Room.class)) {
				return parseRoom(data);
			}
			if (entityClass.equals(Service.class)) {
				return parseService(data);
			}
			return null;
		} catch (ArrayIndexOutOfBoundsException | IncorrectNameException | IncorrectParameterException
				| NumberFormatException | IncorrectIDEcxeption e) {
			logger.log(Level.DEBUG, e.getMessage());
			throw e;
		}

	}

	@SuppressWarnings("rawtypes")
	public static Object defineField(Class fieldType, String data) {
		if (fieldType.equals(Integer.class)) {
			return Integer.valueOf(data);
		}
		if (fieldType.equals(String.class)) {
			return data;
		}
		if (fieldType.equals(Boolean.class)) {
			return Boolean.valueOf(data);
		}
		if (fieldType.equals(RoomStatus.class)) {
			return RoomStatus.valueOf(data);
		}
		if (fieldType.equals(Double.class)) {
			return Double.valueOf(data);
		}
		if (fieldType.equals(Date.class)) {
			Date date = new Date(Long.parseLong(data));
			return date;
		}
		return null;
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
			id = Integer.parseInt(params[ID].trim());
			name = params[NAME].trim();
			Client client = new Client(name);
			client.setId(id);
			return client;
		} catch (IncorrectParameterException | NumberFormatException e) {
			logger.log(Level.DEBUG, e.getMessage());
			throw e;
		}
	}

	public static Room parseRoom(String data)
			throws ArrayIndexOutOfBoundsException, NumberFormatException, IncorrectParameterException {
		try {
			String[] roomData = data.split(",");
			if (roomData.length < 5) {
				throw new IncorrectParameterException();
			}
			Integer id = Integer.parseInt(roomData[ID].trim());
			HashSet<Client> clients = new HashSet<>();
			Integer number = Integer.parseInt(roomData[ROOM_NUMBER].trim());
			Integer capacity = Integer.parseInt(roomData[ROOM_CAPACITY].trim());
			Integer star = Integer.parseInt(roomData[ROOM_STAR].trim());
			RoomStatus status = RoomStatus.valueOf(roomData[ROOM_STATUS].trim());
			Integer pricePerDay = Integer.parseInt(roomData[ROOM_PRICE].trim());
			if (pricePerDay <= 0) {
				throw new IncorrectParameterException();
			}
			for (int i = 0; i < roomData.length - 6 && i < capacity; i++) {
				if (roomData[i + 6] != null) {
					Client client = new Client();
					clients.add(client);
					client.setId(Integer.parseInt(roomData[i + 6].trim()));
				}
			}
			Room room = new Room(number, capacity, star, pricePerDay);
			room.setId(id);
			room.setStatus(status);
			return room;
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectParameterException e) {
			logger.log(Level.DEBUG, e.getMessage());
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
			Room room = new Room();
			Client client = new Client();
			Date orderFrom, orderTo;
			String[] orderData = data.split(",");
			if (orderData.length < 5) {
				throw new IncorrectParameterException();
			}
			services = new ArrayList<Service>();
			id = Integer.parseInt(orderData[ID].trim());
			roomId = Integer.parseInt(orderData[ORDER_ROOM].trim());
			clientId = Integer.parseInt(orderData[ORDER_CLIENT].trim());
			orderFrom = new Date();
			orderFrom.setTime(Long.parseLong(orderData[ORDER_FROM].trim()));
			orderTo = new Date();
			orderTo.setTime(Long.parseLong(orderData[ORDER_TO].trim()));
			for (int i = 0; i < orderData.length - 5;) {
				services.add(parseService(orderData[i++ + 5] + ", " + orderData[i++ + 5] + ", " + orderData[i++ + 5]
						+ ", " + orderData[i++ + 5]));
			}
			room.setId(roomId);
			client.setId(clientId);
			Order order = new Order(room, client, orderFrom, orderTo);
			order.setId(id);
			return order;
		} catch (NumberFormatException | IncorrectIDEcxeption | ArrayIndexOutOfBoundsException
				| IncorrectParameterException e) {
			logger.log(Level.DEBUG, e.getMessage());
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
			Integer id = Integer.parseInt(serviceData[ID].trim());
			Integer price = Integer.parseInt(serviceData[SERVICE_PRICE].trim());
			String name = serviceData[NAME].trim();
			Date date = new Date();
			date.setTime(Long.parseLong(serviceData[SERVICE_DATE].trim()));
			Service service = new Service(price, name);
			service.setId(id);
			return service;
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | IncorrectParameterException e) {
			logger.log(Level.DEBUG, e.getMessage());
			throw e;
		}
	}

}
