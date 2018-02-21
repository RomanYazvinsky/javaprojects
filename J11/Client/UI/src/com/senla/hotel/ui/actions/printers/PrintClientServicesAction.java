package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PrintClientServicesAction implements IAction {
    private static Logger logger = LogManager.getLogger(PrintClientServicesAction.class);


    @SuppressWarnings("unchecked")
    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        Client client = SelectClientAction.getClient();
        try {
            Message request = new Message(PublicAPI.GET_SERVICES_OF_CLIENT, new Object[]{client});
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            ArrayList<Service> services = (ArrayList<Service>) response.getData()[0];

            if (services.size() == 0) {
                logger.log(Level.DEBUG, new EmptyObjectException().getMessage());
                throw new ActionForceStopException();
            }
            for (Service service : services) {
                Printer.printService(service);
            }
            request = new Message(PublicAPI.GET_PRICE_FOR_SERVICES, new Object[]{services});
            writer.writeObject(request);
            response = (Message) reader.readObject();
            Integer price = (Integer) response.getData()[0];
            Printer.println(price.toString());
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
