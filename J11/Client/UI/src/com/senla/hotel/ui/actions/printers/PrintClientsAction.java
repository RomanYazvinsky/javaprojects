package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.entities.Client;
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

public class PrintClientsAction implements IAction {
    private static Logger logger = LogManager.getLogger(PrintClientsAction.class);


    @SuppressWarnings("unchecked")
    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            Message request = new Message(PublicAPI.GET_CLIENTS);
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            ArrayList<Client> clients = (ArrayList<Client>) response.getData()[0];
            if (clients.size() == 0) {
                logger.log(Level.DEBUG, new EmptyObjectException().getMessage());
                throw new ActionForceStopException();
            }
            Printer.printEntities(clients);
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
