package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.api.FacadeAPI;
import com.senla.hotel.api.ui.IAction;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectOrderAction;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PrintPriceForOrderAction implements IAction {
    private static Logger logger = LogManager.getLogger(PrintPriceForOrderAction.class);

    @SuppressWarnings("unchecked")
    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        Order order = SelectOrderAction.getOrder();
        try {
            Message request = new Message(FacadeAPI.GET_PRICE_FOR_ROOM, new Object[]{order});
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            Integer price = (Integer) response.getData()[0];
            request = new Message(FacadeAPI.GET_SERVICES_OF_CLIENT, new Object[]{order.getClient()});
            writer.writeObject(request);
            response = (Message) reader.readObject();

            ArrayList<Service> services = (ArrayList<Service>) response.getData()[0];
            request = new Message(FacadeAPI.GET_PRICE_FOR_SERVICES, new Object[]{services});
            writer.writeObject(request);
            Printer.print(price.toString());
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
