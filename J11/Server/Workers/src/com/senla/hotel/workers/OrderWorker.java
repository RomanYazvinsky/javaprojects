package com.senla.hotel.workers;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.senla.hotel.dao.OrderDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.hotel.api.internal.IClientRepository;
import com.senla.hotel.api.internal.IOrderRepository;
import com.senla.hotel.api.internal.IOrderWorker;
import com.senla.hotel.api.internal.IRoomRepository;
import com.senla.hotel.api.internal.IServiceRepository;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.utilities.CSVModule;

import utilities.*;

public class OrderWorker implements IOrderWorker {
    private static Logger logger = LogManager.getLogger(OrderWorker.class);
    private OrderDao orderDao;

    public OrderWorker() {
        orderDao = new OrderDao(DBConnector.getInstance().getConnection());
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
        for (Order order : orderDao.getAll()) {
            if (order.getClient().equals(client)) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Order> selectByRoom(Room room) {
        ArrayList<Order> result = new ArrayList<>();
        for (Order order : orderDao.getAll()) {
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

/*	@Override
    public ArrayList<Order> getLastClientsOfRoom(Room room, int clientCount) {
		ArrayList<Order> lastOrders = new ArrayList<>();
		for (Order order : sort(selectByRoom(room), new OrderDateComparator())) {
			if (lastOrders.size() < clientCount) {
				lastOrders.add(order);
			}
		}
		return lastOrders;
	}*/

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
        return orderDao.getById(orderID);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.senla.hotel.workers.IOrderWorker#getActualOrders(java.util.Date)
     */
    @Override
    public ArrayList<Order> getActualOrders(Date now) {
        ArrayList<Order> actualOrders = new ArrayList<Order>(orderDao.getAll());
        for (Iterator<Order> i = actualOrders.iterator(); i.hasNext(); ) {
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

    @Override
    public Integer getActualClientCount(Date now) {
        return getActualClients(now).size();
    }

    @Override
    public ArrayList<Room> getFreeRooms(Date date) {
        ArrayList<Room> freeRooms = new ArrayList<Room>();
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
        return clientCount < order.getRoom().getCapacity();
    }

    @Override
    public synchronized Boolean add(Order order, Date date) throws IncorrectIDEcxeption {

        return orderDao.add(order);
    }


    @Override
    public synchronized Boolean add(Order order, boolean addId) {
        return orderDao.add(order);
    }

    @Override
    public ArrayList<Service> getServicesOfClient(Client client) {
        ArrayList<Service> result = new ArrayList<Service>();
        for (Order order : selectByClient(client)) {
            //	result.addAll(order.getServices());
        }
        return result;
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderDao.getById(id);
    }


    @Override
    public ArrayList<Order> getOrders() {
        return orderDao.getAll();
    }

    @Override
    public Boolean closeOrder(Order order, Date now) {
        order.setOrderTo(now);
        return true;
    }

    @Override
    public Integer getPriceForServices(Order order) {
        Integer price = 0;
        /*if (order != null) {
            for (Service service : order.getServices()) {
				price += service.getPrice();
			}
		}*/
        return price;
    }


    @Override
    public String[] toStringArray(ArrayList<Order> orders) {
        List<String> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(order.toString());
        }
        return result.toArray(new String[result.size()]);
    }


    @Override
    public synchronized ArrayList<Order> importAll() throws EmptyObjectException {
        ArrayList<Order> orders = new ArrayList<>();

        CSVModule.importAll(Order.class).forEach(arg0 -> {
            Order order = (Order) arg0;
            orders.add(order);
        });
        return orders;
    }


    @Override
    public synchronized void exportAll() {
        CSVModule.exportAll(getOrders());
    }

    @Override
    public synchronized Boolean delete(Order order) {
        orderDao.delete(order);
        return true;
    }
}
