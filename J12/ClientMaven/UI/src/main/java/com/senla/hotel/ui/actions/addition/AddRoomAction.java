package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Room;
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

public class AddRoomAction implements IAction {
    private static Logger logger = LogManager.getLogger(AddRoomAction.class);


    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        Printer.println(Messages.ASK_FOR_ROOM_PARAMS.toString());
        String[] params = Input.userInput().split(",");
        try {
            Room room = new Room(Integer.parseInt(params[0].trim()), Integer.parseInt(params[1].trim()),
                    Integer.parseInt(params[2].trim()), Integer.parseInt(params[3].trim()));
            Message request = new Message(PublicAPI.ADD_ROOM, new Object[]{room});
            writer.writeObject(request);
            reader.readObject();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | IOException | ClassNotFoundException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
