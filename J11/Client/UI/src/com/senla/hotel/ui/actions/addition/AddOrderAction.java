package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.DateCreator;
import utilities.ui.Input;
import utilities.ui.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddOrderAction implements IAction {
    private static Logger logger = LogManager.getLogger(AddOrderAction.class);


    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        Printer.println(Messages.ASK_FOR_ORDER_PARAMS.toString());
        String[] params = Input.userInput().split(",");
        try {
            Order order = new Order(SelectRoomAction.getRoom(), SelectClientAction.getClient(),
                    DateCreator.parseString(params[0].trim()), DateCreator.parseString(params[1].trim()));
            Message request = new Message(PublicAPI.ADD_ORDER, new Object[]{order});
            writer.writeObject(request);
            reader.readObject();
        } catch (NumberFormatException | EmptyObjectException
                | ArrayIndexOutOfBoundsException | IOException | ClassNotFoundException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
