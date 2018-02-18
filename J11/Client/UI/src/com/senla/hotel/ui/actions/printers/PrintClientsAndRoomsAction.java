package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class PrintClientsAndRoomsAction implements IAction {
    private static Logger logger = LogManager.getLogger(PrintClientsAndRoomsAction.class);


    @SuppressWarnings("unchecked")
    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {

        try {
            Message request = new Message(PublicAPI.GET_CLIENTS);
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            ArrayList<Client> clients = (ArrayList<Client>) response.getData()[0];
            for (Client client : clients) {
                Printer.printClient(client);
                request = new Message(PublicAPI.GET_ACTUAL_ORDER, new Object[]{client, new GregorianCalendar().getTime()});
                writer.writeObject(request);
                response = (Message) reader.readObject();
                Order order = (Order) response.getData()[0];
                Room room = order.getRoom();
                if (room != null) {
                    Printer.printRoom(room);
                } else {
                    logger.log(Level.DEBUG, new EmptyObjectException().getMessage());
                    throw new ActionForceStopException();
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
