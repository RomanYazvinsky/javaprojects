package com.senla.hotel.ui.actions.selectors;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
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

public class SelectServiceAction implements IAction {
    private static Service service;
    private static Logger logger = LogManager.getLogger(SelectServiceAction.class);

    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            Message request = new Message(PublicAPI.GET_SERVICE_BY_ID, new Object[]{Integer.parseInt(Input.userInput())});
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            service = (Service) response.getData()[0];
            Printer.printService(service);

        } catch (NumberFormatException | IndexOutOfBoundsException | ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

    public static Service getService() {
        return service;
    }

}
