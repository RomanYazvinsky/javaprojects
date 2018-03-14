package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.api.FacadeAPI;
import com.senla.hotel.api.ui.IAction;
import com.senla.hotel.constants.Messages;
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

public class AddServiceAction implements IAction {
    private static Logger logger = LogManager.getLogger(AddServiceAction.class);


    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        Printer.println(Messages.ASK_FOR_SERVICE_PARAMS.toString());
        String[] params = Input.userInput().split(",");
        try {
            Service service = new Service(Integer.parseInt(params[0].trim()), params[1].trim());
            Message request = new Message(FacadeAPI.ADD_SERVICE, new Object[]{service});
            writer.writeObject(request);
            reader.readObject();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | IOException | ClassNotFoundException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();

        }
    }

}
