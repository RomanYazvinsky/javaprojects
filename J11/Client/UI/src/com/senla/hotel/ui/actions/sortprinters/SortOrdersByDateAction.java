package com.senla.hotel.ui.actions.sortprinters;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import utilities.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SortOrdersByDateAction implements IAction {
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(SortOrdersByDateAction.class);


    @SuppressWarnings("unchecked")
    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            Message request = new Message(PublicAPI.GET_ORDERS);
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            ArrayList<Order> orders = (ArrayList<Order>) response.getData()[0];
            if (orders.size() == 0) {
                logger.log(Level.DEBUG, new EmptyObjectException().getMessage());
                throw new ActionForceStopException();
            }
            request = new Message(PublicAPI.SORT_ORDERS_BY_DATE, new Object[]{orders});
            writer.writeObject(request);
            reader.readObject();
            Printer.printEntities(orders);
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
