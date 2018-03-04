package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;
import java.util.Date;

public interface IOrderManager {
    ArrayList<Order> selectByClient(Client client) throws QueryFailureException, UnexpectedValueException;

    ArrayList<Order> selectByRoom(Room room) throws QueryFailureException, UnexpectedValueException;

    ArrayList<Order> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException;

    Order getActualOrder(Client client, Date now) throws QueryFailureException, UnexpectedValueException;

    Order getOrderByID(Integer orderID) throws AnalysisException, QueryFailureException, UnexpectedValueException;

    ArrayList<Order> getActualOrders(Date now) throws QueryFailureException, UnexpectedValueException;

    ArrayList<Client> getActualClients(Date now) throws QueryFailureException, UnexpectedValueException;

    Integer getActualClientCount(Date now) throws QueryFailureException, UnexpectedValueException;

    ArrayList<Room> getFreeRooms(Date date);

    Integer getPriceForRoom(Order order);

    Boolean add(Order order, Date date) throws IncorrectIDEcxeption, QueryFailureException, UnexpectedValueException, AnalysisException;

    Boolean add(Order order, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException;

    Order getOrderById(Integer id) throws AnalysisException, QueryFailureException, UnexpectedValueException;

    ArrayList<Order> getOrders() throws QueryFailureException, UnexpectedValueException;

    Boolean closeOrder(Order order, Date now);

    String[] toStringArray(ArrayList<Order> orders);

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Order> importAll() throws EmptyObjectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException;

    Boolean delete(Order order) throws QueryFailureException, AnalysisException;
}
