package com.senla.hotel.facade;

import com.senla.hotel.api.internal.managers.*;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.PropertyNames;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.*;
import com.senla.hotel.properties.HotelProperties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.injection.DependencyInjector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Facade {
    private static Logger logger = LogManager.getLogger(Facade.class);
    private static Facade instance;
    private IRoomManager roomManager;
    private IClientManager clientManager;
    private IServiceManager serviceManager;
    private IOrderManager orderManager;
    private IServiceRecordManager serviceRecordManager;
    private HotelProperties properties;

    private Facade() {
        try {
            roomManager = (IRoomManager) DependencyInjector.newInstance(IRoomManager.class);
            clientManager = (IClientManager) DependencyInjector.newInstance(IClientManager.class);
            serviceManager = (IServiceManager) DependencyInjector.newInstance(IServiceManager.class);
            orderManager = (IOrderManager) DependencyInjector.newInstance(IOrderManager.class);
            serviceRecordManager = (IServiceRecordManager) DependencyInjector.newInstance(IServiceRecordManager.class);
            properties = HotelProperties.getInstance(Constants.PATH_TO_PROPERTIES);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public Boolean addClient(Client client) throws InternalErrorException {
        Boolean result;
        try {
            result = clientManager.add(client, true);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return result;
    }

    public Boolean addOrder(Order order, Date date) throws InternalErrorException {
        Boolean result;
        try {
            result = orderManager.add(order);
            return result;
        } catch (IncorrectIDEcxeption | QueryFailureException | AnalysisException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();

        }
    }

    public Boolean addRoom(Room room) throws InternalErrorException {
        Boolean result = null;
        try {
            result = roomManager.add(room, true);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return result;
    }

    public Boolean addService(Service service) throws InternalErrorException {
        Boolean result = null;
        try {
            result = serviceManager.add(service, true);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return result;
    }

    public Room getRoomByID(Integer roomID) throws InternalErrorException {
        Room room = null;
        try {
            room = roomManager.getRoomByID(roomID);
        } catch (AnalysisException | QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return room;
    }

    public Client getClientByID(Integer clientID) throws UnexpectedValueException, InternalErrorException {
        Client client = null;
        try {
            client = clientManager.getClientByID(clientID);
        } catch (QueryFailureException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return client;
    }

    public Order getOrderByID(Integer orderID) throws UnexpectedValueException, InternalErrorException {
        Order order = null;
        try {
            order = orderManager.getOrderByID(orderID);
        } catch (AnalysisException | QueryFailureException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return order;
    }

    public Service getServiceByID(Integer serviceID) throws InternalErrorException {
        Service service = null;
        try {
            service = serviceManager.getServiceByID(serviceID);
        } catch (AnalysisException | QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return service;
    }

    public List<Room> getRooms() throws InternalErrorException {
        try {
            return roomManager.getRooms();
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }


    public List<Order> getOrders() throws InternalErrorException {
        try {
            return orderManager.getOrders();
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public ArrayList<Order> getActualOrders(Date date) throws InternalErrorException {
        try {
            return orderManager.getActualOrders(date);
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public List<Client> getClients() throws InternalErrorException {
        try {
            return clientManager.getClients();
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }

    }

    public Integer getFreeRoomsCount(Date date) throws InternalErrorException {
        Integer result = null;
        try {
            result = orderManager.getFreeRooms(date).size();
        } catch (QueryFailureException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return result;
    }

    public Integer getActualClientCount(Date date) throws InternalErrorException {
        Integer result = null;
        try {
            result = orderManager.getActualClients(date).size();
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return result;
    }

    public List<Room> getFreeRooms(Date date) throws InternalErrorException {
        List<Room> rooms = null;
        try {
            rooms = orderManager.getFreeRooms(date);
        } catch (QueryFailureException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return rooms;
    }

    public Integer getPriceForRoom(Order order) {
        Integer result = orderManager.getPriceForRoom(order);
        return result;
    }

    public Integer getPriceForServices(ArrayList<Service> services) {
        return serviceManager.getPriceForServices(services);
    }


    public List<Service> getServices() throws InternalErrorException {
        List<Service> services = null;
        try {
            services = serviceManager.getServices();
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return services;
    }

    public Boolean closeOrder(Order order, Date now) {
        Boolean result = orderManager.closeOrder(order, now);
        return result;
    }


    public Order getActualOrder(Client client, Date now) throws InternalErrorException {
        Order order = null;
        try {
            order = orderManager.getActualOrder(client, now);
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        return order;
    }

    public List<Client> getActualClients(Date date) throws InternalErrorException {
        try {
            return orderManager.getActualClients(date);
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public Boolean setRoomStatus(Room room, RoomStatus status) throws InternalErrorException {
        try {
            if (properties.getProperty(PropertyNames.CHANGE_ROOMSTATUS_ABILITY.toString()).equals("0")) {
                return false;
            }
        } catch (EmptyObjectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
        room.setStatus(status);
        return false;
    }

    public ArrayList<Client> importClients() throws InternalErrorException {
        try {
            return clientManager.importAll();
        } catch (EmptyObjectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public ArrayList<Order> importOrders() throws InternalErrorException {
        try {
            return orderManager.importAll();
        } catch (EmptyObjectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public ArrayList<Room> importRooms() throws InternalErrorException {
        try {
            return roomManager.importAll();
        } catch (EmptyObjectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public ArrayList<Service> importServices() throws InternalErrorException {
        try {
            return serviceManager.importAll();
        } catch (EmptyObjectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public void exportClients() throws InternalErrorException {
        try {
            clientManager.exportAll();
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public void exportOrders() throws InternalErrorException {
        try {
            orderManager.exportAll();
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public void exportRooms() throws InternalErrorException {
        try {
            roomManager.exportAll();
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public void exportServices() throws InternalErrorException {
        try {
            serviceManager.exportAll();
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public Boolean deleteClient(Client client) throws InternalErrorException {
        try {
            return clientManager.delete(client);
        } catch (QueryFailureException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public Boolean deleteOrder(Order order) throws InternalErrorException {
        try {
            return orderManager.delete(order);
        } catch (QueryFailureException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public Boolean deleteRoom(Room room) throws InternalErrorException {
        try {
            return roomManager.delete(room);
        } catch (QueryFailureException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public Boolean deleteService(Service service) throws InternalErrorException {
        try {
            return serviceManager.delete(service);
        } catch (QueryFailureException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public Boolean addClientWithID(Client client) throws InternalErrorException {
        try {
            return clientManager.add(client, false);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public Boolean addRoomWithID(Room room) throws AnalysisException, InternalErrorException {
        try {
            return roomManager.add(room, false);
        } catch (QueryFailureException | UnexpectedValueException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public Boolean addOrderWithID(Order order) throws InternalErrorException {
        try {
            return orderManager.add(order, false);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }

    public Boolean addServiceWithID(Service service) throws InternalErrorException {
        try {
            return serviceManager.add(service, false);
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException | DatabaseConnectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new InternalErrorException();
        }
    }


}
