package com.senla.hotel.ui.actions.selectors;

import com.senla.hotel.api.FacadeAPI;
import com.senla.hotel.api.ui.IAction;
import com.senla.hotel.entities.Client;
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

public class SelectClientAction implements IAction {
    private static Client client;
    private static Logger logger = LogManager.getLogger(SelectClientAction.class);


    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            Message request = new Message(FacadeAPI.GET_CLIENT_BY_ID, new Object[]{Integer.parseInt(Input.userInput())});
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            client = (Client) response.getData()[0];

            if (client == null) {
                throw new ActionForceStopException();
            }
            Printer.printClient(client);
        } catch (NumberFormatException | IndexOutOfBoundsException | IOException | ClassNotFoundException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();

        }
    }

    public static Client getClient() {
        return client;
    }

}
