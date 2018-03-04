package com.senla.hotel.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.OrderDao;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManager implements com.senla.hotel.api.internal.IOrderManager {
    private static Logger logger = LogManager.getLogger(OrderManager.class);
    private OrderDao orderDao;

    public OrderManager() throws DatabaseConnectException {
        try {
            orderDao = new OrderDao();
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
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
    public ArrayList<Order> selectByClient(Client client) throws QueryFailureException {
        ArrayList<Order> result = new ArrayList<>();
        try {
            for (Order order : orderDao.getAll()) {
                if (order.getClient().equals(client)) {
                    result.add(order);
                }
            }
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public ArrayList<Order> selectByRoom(Room room) throws QueryFailureException {
        ArrayList<Order> result = new ArrayList<>();
        try {
            for (Order order : orderDao.getAll()) {
                if (order.getRoom().equals(room)) {
                    result.add(order);
                }
            }
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return result;
    }

    @Override
    public ArrayList<Order> sort(SortType sortType) throws QueryFailureException {
        try {
            return orderDao.getAll(sortType);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public Order getActualOrder(Client client, Date now) throws QueryFailureException {
        try {
            for (Order order : selectByClient(client)) {
                if (order.isActive(now)) {
                    return order;
                }
            }
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return null;
    }

    @Override
    public Order getOrderByID(Integer orderID) throws QueryFailureException {
        if (orderID == null) {
            return null;
        }
        try {
            return orderDao.getById(orderID);
        } catch (QueryFailureException  e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public ArrayList<Order> getActualOrders(Date now) throws QueryFailureException {
        ArrayList<Order> actualOrders = null;
        try {
            actualOrders = new ArrayList<Order>(orderDao.getAll());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        actualOrders.removeIf(order -> !order.isActive(now) || order.getRoom().isOnService());
        return actualOrders;
    }

    @Override
    public ArrayList<Client> getActualClients(Date now) throws QueryFailureException {
        try {
            return makeClientList(getActualOrders(now));
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;

        }
    }

    @Override
    public Integer getActualClientCount(Date now) throws QueryFailureException {
        try {
            return getActualClients(now).size();
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
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
    public synchronized Boolean add(Order order, Date date) throws QueryFailureException {
        try {
            return orderDao.add(order);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized Boolean add(Order order, boolean addId) throws QueryFailureException {
        try {
            return orderDao.add(order);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public Order getOrderById(Integer id) throws  QueryFailureException {
        try {
            return orderDao.getById(id);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }


    @Override
    public ArrayList<Order> getOrders() throws QueryFailureException {
        try {
            return orderDao.getAll();
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public Boolean closeOrder(Order order, Date now) {
        order.setOrderTo(now);
        return true;
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
    public synchronized void updateByImport() throws  QueryFailureException {
        try {
            orderDao.batchCreateOrUpdate(importAll());
        } catch ( QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }

    }

    @Override
    public synchronized ArrayList<Order> importAll()  {
        ArrayList<Order> orders = new ArrayList<>();

        CSVModule.importAll(Order.class).forEach(arg0 -> {
            Order order = (Order) arg0;
            orders.add(order);
        });
        return orders;
    }


    @Override
    public synchronized void exportAll() throws QueryFailureException {
        try {
            CSVModule.exportAll(getOrders());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized Boolean delete(Order order) throws QueryFailureException {
        try {
            orderDao.delete(order);
        } catch ( QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
