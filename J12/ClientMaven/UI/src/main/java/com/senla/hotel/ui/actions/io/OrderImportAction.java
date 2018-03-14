package com.senla.hotel.ui.actions.io;

import com.senla.hotel.api.FacadeAPI;
import com.senla.hotel.api.ui.IAction;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Input;
import utilities.ui.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class OrderImportAction implements IAction {
    private static Logger logger = LogManager.getLogger(OrderImportAction.class);


    @SuppressWarnings("unchecked")
    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {

        try {
            Message request = new Message(FacadeAPI.IMPORT_CLIENTS, null);
            writer.writeObject(request);
            Message response = (Message) reader.readObject();
            ArrayList<Client> clients = (ArrayList<Client>) response.getData()[0];

            request = new Message(FacadeAPI.IMPORT_ROOMS, null);
            writer.writeObject(request);
            response = (Message) reader.readObject();
            ArrayList<Room> rooms = (ArrayList<Room>) response.getData()[0];

            request = new Message(FacadeAPI.IMPORT_ORDERS, null);
            writer.writeObject(request);
            response = (Message) reader.readObject();
            ArrayList<Order> orders = (ArrayList<Order>) response.getData()[0];

            request = new Message(FacadeAPI.IMPORT_SERVICES, null);
            writer.writeObject(request);
            response = (Message) reader.readObject();
            ArrayList<Service> services = (ArrayList<Service>) response.getData()[0];
            Integer i = 1;
            for (Order order : orders) {
                for (Client client : clients) {
                    if (client.getId().equals(order.getClient().getId())) {
                        request = new Message(FacadeAPI.ADD_CLIENT_WITH_ID, new Object[]{client});
                        writer.writeObject(request);
                        reader.readObject();
                        order.setClient(client);
                        break;
                    }
                }
                for (Room room : rooms) {
                    if (room.getId().equals(order.getRoom().getId())) {
                        request = new Message(FacadeAPI.ADD_ROOM_WITH_ID, new Object[]{room});
                        writer.writeObject(request);
                        reader.readObject();
                        order.setRoom(room);
                        break;
                    }
                }
               /* for (Service service : order.getServices()) {
                    for (Service tempService : managers) {
                        if (service.getId().equals(tempService.getId())) {
                            service = tempService;
                        }
                    }
                }*/
                Printer.println(i.toString() + ") " + order.toString());
                i++;
            }
            i = Integer.parseInt(Input.userInput()) - 1;
            Order order = orders.get(i);

            request = new Message(FacadeAPI.DELETE_ORDER, new Object[]{order});
            writer.writeObject(request);
            reader.readObject();

            request = new Message(FacadeAPI.ADD_ORDER_WITH_ID, new Object[]{order});
            writer.writeObject(request);
            reader.readObject();

        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
