package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.api.FacadeAPI;
import com.senla.hotel.api.ui.IAction;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PrintServicesAction implements IAction {
    private static Logger logger = LogManager.getLogger(PrintServicesAction.class);


    @SuppressWarnings("unchecked")
    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {

        try {
            Message request = new Message(FacadeAPI.GET_SERVICES);
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            ArrayList<Service> entities = (ArrayList<Service>) response.getData()[0];
            if (entities.size() == 0) {
                logger.log(Level.DEBUG, new EmptyObjectException().getMessage());
                throw new ActionForceStopException();
            }
            for (Service service : entities) {
                Printer.printService(service);
            }
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
