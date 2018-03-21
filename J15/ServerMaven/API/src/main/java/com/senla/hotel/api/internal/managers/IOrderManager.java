package com.senla.hotel.api.internal.managers;

import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IOrderManager extends IGenericManager<Order> {
    ArrayList<Order> selectByClient(Client client) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Order> selectByRoom(Room room) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Order getActualOrder(Client client, Date now) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;


    ArrayList<Order> getActualOrders(Date now) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    List<Client> getActualClients(Date now) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    List<Room> getFreeRooms(Date date) throws QueryFailureException, DatabaseConnectException;

    Integer getPriceForRoom(Order order);

    Boolean add(Order order) throws IncorrectIDEcxeption, QueryFailureException, UnexpectedValueException, AnalysisException, DatabaseConnectException;

    Boolean closeOrder(Order order, Date now);

    String[] toStringArray(ArrayList<Order> orders);

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<Order> importAll() throws EmptyObjectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

}
