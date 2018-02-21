package com.senla.hotel.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.OrderDao;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.*;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.senla.hotel.dao.com.senla.hotel.dao.connector.DBConnector;

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
    public ArrayList<Order> selectByClient(Client client) throws QueryFailureException, UnexpectedValueException {
        ArrayList<Order> result = new ArrayList<>();
        try {
            for (Order order : orderDao.getAll()) {
                if (order.getClient().equals(client)) {
                    result.add(order);
                }
            }
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public ArrayList<Order> selectByRoom(Room room) throws QueryFailureException, UnexpectedValueException {
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
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public ArrayList<Order> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException {
        try {
            return orderDao.getAll(sortType);
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Order getActualOrder(Client client, Date now) throws QueryFailureException, UnexpectedValueException {
        try {
            for (Order order : selectByClient(client)) {
                if (order.isActive(now)) {
                    return order;
                }
            }
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
        return null;
    }

    @Override
    public Order getOrderByID(Integer orderID) throws AnalysisException, QueryFailureException, UnexpectedValueException {
        if (orderID == null) {
            return null;
        }
        try {
            return orderDao.getById(orderID);
        } catch (QueryFailureException | AnalysisException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Order> getActualOrders(Date now) throws QueryFailureException, UnexpectedValueException {
        ArrayList<Order> actualOrders = null;
        try {
            actualOrders = new ArrayList<Order>(orderDao.getAll());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
        actualOrders.removeIf(order -> !order.isActive(now) || order.getRoom().isOnService());
        return actualOrders;
    }

    @Override
    public ArrayList<Client> getActualClients(Date now) throws QueryFailureException, UnexpectedValueException {
        try {
            return makeClientList(getActualOrders(now));
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;

        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer getActualClientCount(Date now) throws QueryFailureException, UnexpectedValueException {
        try {
            return getActualClients(now).size();
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
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
    public synchronized Boolean add(Order order, Date date) throws IncorrectIDEcxeption, QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            return orderDao.add(order);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized Boolean add(Order order, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            return orderDao.add(order);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public Order getOrderById(Integer id) throws AnalysisException, QueryFailureException, UnexpectedValueException {
        try {
            return orderDao.getById(id);
        } catch (QueryFailureException | AnalysisException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


    @Override
    public ArrayList<Order> getOrders() throws QueryFailureException, UnexpectedValueException {
        try {
            return orderDao.getAll();
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
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
    public synchronized void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException {
        DBConnector dbConnector = DBConnector.getInstance();
        try {
            dbConnector.startTransaction();
            for (Order order : importAll()) {
                orderDao.createOrUpdate(order);
            }
            dbConnector.finishTransaction();
        } catch (EmptyObjectException | QueryFailureException | AnalysisException | UnexpectedValueException | DatabaseConnectException e) {
            dbConnector.finishTransaction(true);
            logger.log(Level.DEBUG, e);
            throw e;
        }

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
    public synchronized void exportAll() throws QueryFailureException, UnexpectedValueException {
        try {
            CSVModule.exportAll(getOrders());
        } catch (QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public synchronized Boolean delete(Order order) throws QueryFailureException, AnalysisException {
        try {
            orderDao.delete(order);
        } catch (AnalysisException | QueryFailureException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
