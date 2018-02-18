package com.senla.hotel.ui.actions.selectors;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.Input;
import utilities.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SelectRoomAction implements IAction {
    private static Room room;
    private static Logger logger = LogManager.getLogger(SelectRoomAction.class);


    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            Message request = new Message(PublicAPI.GET_ROOM_BY_ID, new Object[]{Integer.parseInt(Input.userInput())});
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            room = (Room) response.getData()[0];
            Printer.printRoom(room);
        } catch (NumberFormatException | IndexOutOfBoundsException | ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

    public static Room getRoom() throws EmptyObjectException {
        if (room == null) {
            throw new EmptyObjectException();
        }
        return room;
    }

}
