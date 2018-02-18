package com.senla.hotel.ui.actions.io;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OrdersExportAction implements IAction {
    private static Logger logger = LogManager.getLogger(OrdersExportAction.class);

    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            Message request = new Message(PublicAPI.EXPORT_ORDERS, null);
            writer.writeObject(request);
            reader.readObject();

            request = new Message(PublicAPI.EXPORT_CLIENTS, null);
            writer.writeObject(request);
            reader.readObject();

            request = new Message(PublicAPI.EXPORT_ROOMS, null);
            writer.writeObject(request);
            reader.readObject();

            request = new Message(PublicAPI.EXPORT_SERVICES, null);
            writer.writeObject(request);
            reader.readObject();
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
