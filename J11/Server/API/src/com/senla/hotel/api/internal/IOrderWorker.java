package com.senla.hotel.api.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.*;

public interface IOrderWorker {

    ArrayList<Order> selectByClient(Client client) throws QueryFailureException;

    ArrayList<Order> selectByRoom(Room room) throws QueryFailureException;


    Order getActualOrder(Client client, Date now) throws QueryFailureException;

    Order getOrderByID(Integer orderID) throws AnalysisException, QueryFailureException;

    ArrayList<Order> getActualOrders(Date now) throws QueryFailureException;

    ArrayList<Client> getActualClients(Date now) throws QueryFailureException;

    Integer getActualClientCount(Date now) throws QueryFailureException;

    ArrayList<Room> getFreeRooms(Date date);

    Integer getPriceForRoom(Order order);

    Boolean add(Order order, Date date) throws IncorrectIDEcxeption, QueryFailureException, UnexpectedValueException, AnalysisException;

    Boolean add(Order order, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException;


    Order getOrderById(Integer id) throws AnalysisException, QueryFailureException;

    ArrayList<Order> getOrders() throws QueryFailureException;

    Boolean closeOrder(Order order, Date now);


    String[] toStringArray(ArrayList<Order> orders);

    ArrayList<Order> importAll() throws EmptyObjectException;

    void exportAll() throws QueryFailureException;

    Boolean delete(Order order) throws QueryFailureException, AnalysisException;

}