package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.Input;
import utilities.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddClientAction implements IAction {
    private static Logger logger = LogManager.getLogger(AddClientAction.class);

    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        Printer.println(Messages.ASK_FOR_CLIENT_PARAMS.toString());
        String userInput = Input.userInput();
        try {
            Client client = new Client(userInput);
            Message request = new Message(PublicAPI.ADD_CLIENT, new Object[]{client});
            writer.writeObject(request);
            reader.readObject();
        } catch (NumberFormatException | NullPointerException | IOException | ClassNotFoundException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
