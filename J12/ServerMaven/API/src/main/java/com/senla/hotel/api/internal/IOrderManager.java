package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;
import java.util.Date;

public interface IOrderManager {
    ArrayList<Order> selectByClient(Client client) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Order> selectByRoom(Room room) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Order> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Order getActualOrder(Client client, Date now) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Order getOrderByID(Integer orderID) throws AnalysisException, QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Order> getActualOrders(Date now) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Client> getActualClients(Date now) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Room> getFreeRooms(Date date) throws QueryFailureException, DatabaseConnectException;

    Integer getPriceForRoom(Order order);

    Boolean add(Order order) throws IncorrectIDEcxeption, QueryFailureException, UnexpectedValueException, AnalysisException, DatabaseConnectException;

    Boolean add(Order order, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException, DatabaseConnectException;

    Order getOrderById(Integer id) throws AnalysisException, QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Order> getOrders() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Boolean closeOrder(Order order, Date now);

    String[] toStringArray(ArrayList<Order> orders);

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Order> importAll() throws EmptyObjectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Boolean delete(Order order) throws QueryFailureException, AnalysisException, DatabaseConnectException;
}
