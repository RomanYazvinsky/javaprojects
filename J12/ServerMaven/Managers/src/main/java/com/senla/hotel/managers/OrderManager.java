package com.senla.hotel.managers;

import com.senla.hotel.api.internal.managers.IOrderManager;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.ClientDao;
import com.senla.hotel.dao.OrderDao;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManager implements IOrderManager {
    private static Logger logger = LogManager.getLogger(OrderManager.class);
    private OrderDao orderDao;
    private ClientDao clientDao;
    private RoomDao roomDao;

    public OrderManager() throws DatabaseConnectException {
        try {
            orderDao = new OrderDao();
            clientDao = new ClientDao();
            roomDao = new RoomDao();
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    private Boolean isActive(Order order, Date now) {
        if (order.getOrderFrom().before(now) && order.getOrderTo().after(now)) {
            return true;
        }
        return false;
    }


    @Override
    public ArrayList<Order> selectByClient(Client client) throws QueryFailureException, DatabaseConnectException {
        ArrayList<Order> result;
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Order.class);
            criteria.add(Restrictions.eq("client_id", client));
            result = new ArrayList(orderDao.get(criteria));
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return result;
    }

    public Integer compareDates(Order order, Order other) {
        if (order.getOrderTo().before(other.getOrderFrom())) {
            return -1;
        }
        if (order.getOrderFrom().after(other.getOrderTo())) {
            return 1;
        }
        return 0;
    }

    @Override
    public ArrayList<Order> selectByRoom(Room room) throws QueryFailureException, DatabaseConnectException {
        ArrayList<Order> result;
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Order.class);
            criteria.add(Restrictions.eq("room_id", room));
            result = new ArrayList(orderDao.get(criteria));
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return result;
    }

    @Override
    public List<Order> sort(SortType sortType) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            List result = orderDao.getAll(session, sortType);
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public Order getActualOrder(Client client, Date now) throws QueryFailureException, DatabaseConnectException {
        ArrayList<Order> result;
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Order.class);
            criteria.add(Restrictions.and(Restrictions.eq("client_id", client), Restrictions.le("date_from", now)));
            result = new ArrayList(orderDao.get(criteria));
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return result.get(0);
    }


    @Override
    public Order getOrderByID(Integer orderID) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Order result = orderDao.read(session, orderID);
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public ArrayList<Order> getActualOrders(Date now) throws QueryFailureException, DatabaseConnectException {
        ArrayList result;
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Order.class);
            criteria.add(Restrictions.le("date_from", now)).add(Restrictions.ge("date_to", now));
            result = new ArrayList(orderDao.get(criteria));
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public List<Client> getActualClients(Date now) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        List<Client> result;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Criteria selectActualOrders = session.createCriteria(Order.class);
            selectActualOrders.add(Restrictions.le("date_from", now)).add(Restrictions.ge("date_to", now));
            result = clientDao.getAll(session);
            transaction.commit();
            return result;
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }


    @Override
    public List<Room> getFreeRooms(Date date) throws QueryFailureException, DatabaseConnectException {
        List<Room> freeRooms = new ArrayList<Room>();
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            freeRooms = roomDao.getAll(session);
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
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

    public synchronized Boolean add(Order order) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            orderDao.create(session, order);
            transaction.commit();
            return true;
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized Boolean add(Order order, boolean addId) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            orderDao.update(session, order);
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }

    @Override
    public Order getOrderById(Integer id) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Order order = orderDao.read(session, id);
            transaction.commit();
            return order;
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }


    @Override
    public List<Order> getOrders() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            List<Order> orders = orderDao.getAll(session);
            transaction.commit();
            return orders;
        } catch (QueryFailureException | DatabaseConnectException e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
    public synchronized void updateByImport() throws QueryFailureException, DatabaseConnectException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            for (Order order : importAll()) {
                orderDao.createOrUpdate(session, order);
            }
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized ArrayList<Order> importAll() {
        ArrayList<Order> orders = new ArrayList<>();

        CSVModule.importAll(Order.class).forEach(arg0 -> {
            Order order = (Order) arg0;
            orders.add(order);
        });
        return orders;
    }


    @Override
    public synchronized void exportAll() throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            CSVModule.exportAll(orderDao.getAll(session));
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    @Override
    public synchronized Boolean delete(Order order) throws QueryFailureException, DatabaseConnectException {
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            orderDao.delete(session, order);
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
        return true;
    }
}
