package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IClientRepository;
import com.senla.hotel.api.internal.IOrderRepository;
import com.senla.hotel.api.internal.IOrderWorker;
import com.senla.hotel.api.internal.IRoomRepository;
import com.senla.hotel.api.internal.IServiceRepository;
import com.senla.hotel.comparators.order.OrderDateComparator;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.utilities.CSVModule;

import utilities.DependencyInjector;
import utilities.Loader;
import utilities.Saver;

public class OrderWorker implements IOrderWorker {
	private static Logger logger;
	private IOrderRepository orderRepository;
	private IClientRepository clientRepository;
	private IRoomRepository roomRepository;
	private IServiceRepository serviceRepository;
	static {
		logger = Logger.getLogger(OrderWorker.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	public OrderWorker() {
		orderRepository = (IOrderRepository) DependencyInjector.newInstance(IOrderRepository.class);
		clientRepository = (IClientRepository) DependencyInjector.newInstance(IClientRepository.class);
		roomRepository = (IRoomRepository) DependencyInjector.newInstance(IRoomRepository.class);
		serviceRepository = (IServiceRepository) DependencyInjector.newInstance(IServiceRepository.class);
	}

	private ArrayList<Client> makeClientList(ArrayList<Order> orders) {
		ArrayList<Client> clients = new ArrayList<Client>();
		for (Order order : orders) {
			clients.add(order.getClient());
		}
		return clients;
	}

	private ArrayList<Room> makeRoomList(ArrayList<Order> orders) {
		ArrayList<Room> rooms = new ArrayList<>();
		for (Order order : orders) {
			rooms.add(order.getRoom());
		}
		return rooms;
	}

	@Override
	public ArrayList<Order> selectByClient(Client client) {
		ArrayList<Order> result = new ArrayList<>();
		for (Order order : orderRepository.get()) {
			if (order.getClient().equals(client)) {
				result.add(order);
			}
		}
		return result;
	}

	@Override
	public ArrayList<Order> selectByRoom(Room room) {
		ArrayList<Order> result = new ArrayList<>();
		for (Order order : orderRepository.get()) {
			if (order.getRoom().equals(room)) {
				result.add(order);
			}
		}
		return result;
	}

	@Override
	public ArrayList<Order> sort(ArrayList<Order> list, Comparator<Order> comparator) {
		Collections.sort(list, comparator);
		return list;
	}

	@Override
	public ArrayList<Order> getLastClientsOfRoom(Room room, int clientCount) {
		ArrayList<Order> lastOrders = new ArrayList<>();
		for (Order order : sort(selectByRoom(room), new OrderDateComparator())) {
			if (lastOrders.size() < clientCount) {
				lastOrders.add(order);
			}
		}
		return lastOrders;
	}

	@Override
	public Order getActualOrder(Client client, Date now) {
		for (Order order : selectByClient(client)) {
			if (order.isActive(now)) {
				return order;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#getOrderByID(java.lang.Integer)
	 */
	@Override
	public Order getOrderByID(Integer orderID) {
		if (orderID == null) {
			return null;
		}
		return (Order) orderRepository.getByID(orderID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#getActualOrders(java.util.Date)
	 */
	@Override
	public ArrayList<Order> getActualOrders(Date now) {
		ArrayList<Order> actualOrders = new ArrayList<Order>(orderRepository.get());
		for (Iterator<Order> i = actualOrders.iterator(); i.hasNext();) {
			Order order = i.next();
			if (!order.isActive(now) || order.getRoom().isOnService()) {
				i.remove();
			}
		}
		return actualOrders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#getActualClients(java.util.Date)
	 */
	@Override
	public ArrayList<Client> getActualClients(Date now) {
		return makeClientList(getActualOrders(now));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.senla.hotel.workers.IOrderWorker#getActualClientCount(java.util.Date)
	 */
	@Override
	public Integer getActualClientCount(Date now) {
		return getActualClients(now).size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#getFreeRooms(java.util.Date)
	 */
	@Override
	public ArrayList<Room> getFreeRooms(Date date) {
		ArrayList<Room> usedRooms = makeRoomList(getActualOrders(date));
		ArrayList<Room> freeRooms = new ArrayList<Room>(roomRepository.get());
		freeRooms.removeAll(usedRooms);
		for (Iterator<Room> i = freeRooms.iterator(); i.hasNext();) {
			if (i.next().isOnService()) {
				i.remove();
			}
		}
		return freeRooms;
	}

	@Override
	public Integer getPriceForRoom(Order order) {
		if (order == null)
			return 0;
		long milliseconds = order.getOrderTo().getTime() - order.getOrderFrom().getTime();
		int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
		return days * order.getRoom().getPricePerDay();
	}

	private Boolean isRoomAvailable(Order order) {
		int clientCount = 0;
		for (Order iterator : orderRepository.get()) {
			if (order.compareDates(iterator) == 0 && order.getRoom().equals(iterator.getRoom())) {
				clientCount++;
			}
		}
		return clientCount < order.getRoom().getCapacity();
	}

	@Override
	public Boolean add(Order order, Date date) throws IncorrectIDEcxeption {
		if (order.getRoom() == null || order.getRoom().getStatus().equals(RoomStatus.ONSERVICE_NOW)
				|| order.getClient() == null || !((IServiceRepository)serviceRepository).checkServices(order.getServices())
				|| !isRoomAvailable(order) || !clientRepository.get().contains(order.getClient())
				|| !roomRepository.get().contains(order.getRoom())) {
			return false;
		}
		return orderRepository.add(order, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#add(com.senla.hotel.entities.Order,
	 * boolean)
	 */
	@Override
	public Boolean add(Order order, boolean addId) {
		return orderRepository.add(order, addId);
	}

	@Override
	public ArrayList<Service> getServicesOfClient(Client client) {
		ArrayList<Service> result = new ArrayList<Service>();
		for (Order order : selectByClient(client)) {
			result.addAll(order.getServices());
		}
		return result;
	}

	@Override
	public Order getOrderById(Integer id) {
		return (Order) orderRepository.getByID(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#getOrders()
	 */
	@Override
	public ArrayList<Order> getOrders() {
		return orderRepository.get();
	}

	@Override
	public Boolean closeOrder(Order order, Date now) {
		order.setOrderTo(now);
		return true;
	}

	@Override
	public Integer getPriceForServices(Order order) {
		Integer price = 0;
		if (order != null) {
			for (Service service : order.getServices()) {
				price += service.getPrice();
			}
		}
		return price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#toStringArray(java.util.ArrayList)
	 */
	@Override
	public String[] toStringArray(ArrayList<Order> orders) {
		List<String> result = new ArrayList<>();
		for (Order order : orders) {
			result.add(order.toString());
		}
		return result.toArray(new String[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#load(java.lang.String)
	 */
	@Override
	public void load(String path) throws ClassNotFoundException, IOException, EmptyObjectException {
		try {
			orderRepository.set(Loader.loadOrders(path));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#save(java.lang.String)
	 */
	@Override
	public void save(String path) throws IOException {
		try {
			Saver.saveOrders(path, orderRepository.get());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#importAll()
	 */
	@Override
	public ArrayList<Order> importAll() throws EmptyObjectException {
		ArrayList<Order> orders = new ArrayList<>();

		CSVModule.importAll(Order.class).forEach(new Consumer<Object>() {

			@Override
			public void accept(Object arg0) {
				Order order = (Order) arg0;
				orders.add(order);
			}
		});
		return orders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.senla.hotel.workers.IOrderWorker#exportAll()
	 */
	@Override
	public void exportAll() {
		CSVModule.exportAll(getOrders());
	}

	@Override
	public Boolean delete(Order order) {
		return orderRepository.delete(order);
	}
}
