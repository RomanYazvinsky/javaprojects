package com.senla.hotel.api.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;

public interface IOrderWorker {

	ArrayList<Order> selectByClient(Client client);

	ArrayList<Order> selectByRoom(Room room);

	ArrayList<Order> sort(ArrayList<Order> list, Comparator<Order> comparator);


	Order getActualOrder(Client client, Date now);

	Order getOrderByID(Integer orderID);

	ArrayList<Order> getActualOrders(Date now);

	ArrayList<Client> getActualClients(Date now);

	Integer getActualClientCount(Date now);

	ArrayList<Room> getFreeRooms(Date date);

	Integer getPriceForRoom(Order order);

	Boolean add(Order order, Date date) throws IncorrectIDEcxeption;

	Boolean add(Order order, boolean addId);

	ArrayList<Service> getServicesOfClient(Client client);

	Order getOrderById(Integer id);

	ArrayList<Order> getOrders();

	Boolean closeOrder(Order order, Date now);

	Integer getPriceForServices(Order order);

	String[] toStringArray(ArrayList<Order> orders);

	ArrayList<Order> importAll() throws EmptyObjectException;

	void exportAll();

	Boolean delete(Order order);

}