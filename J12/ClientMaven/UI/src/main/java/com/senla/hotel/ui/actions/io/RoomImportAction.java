package com.senla.hotel.ui.actions.io;

import com.senla.hotel.api.FacadeAPI;
import com.senla.hotel.api.ui.IAction;
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
import java.util.ArrayList;

public class RoomImportAction implements IAction {
    private static Logger logger = LogManager.getLogger(RoomImportAction.class);


    @SuppressWarnings("unchecked")
    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {

        try {
            Message request = new Message(FacadeAPI.IMPORT_ROOMS, null);
            writer.writeObject(request);
            Message response = (Message) reader.readObject();
            ArrayList<Room> rooms = (ArrayList<Room>) response.getData()[0];
            Integer i = 1;
            for (Room room : rooms) {
                Printer.println(i.toString() + ") " + room.toString());
                i++;
            }
            i = Integer.parseInt(Input.userInput()) - 1;
            Room room = rooms.get(i);

            request = new Message(FacadeAPI.DELETE_ROOM, new Object[]{room});
            writer.writeObject(request);
            reader.readObject();
            request = new Message(FacadeAPI.ADD_ROOM_WITH_ID, new Object[]{room});
            writer.writeObject(request);
            reader.readObject();

        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }

    }

}
