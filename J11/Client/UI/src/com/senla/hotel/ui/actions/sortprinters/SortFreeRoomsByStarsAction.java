package com.senla.hotel.ui.actions.sortprinters;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class SortFreeRoomsByStarsAction implements IAction {

    private static Logger logger = LogManager.getLogger(SortFreeRoomsByStarsAction.class);


    @SuppressWarnings("unchecked")
    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            Message request = new Message(PublicAPI.GET_FREE_ROOMS, new Object[]{new GregorianCalendar().getTime()});
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            ArrayList<Room> rooms = (ArrayList<Room>) response.getData()[0];
            if (rooms.size() == 0) {
                logger.log(Level.DEBUG, new EmptyObjectException().getMessage());
                throw new ActionForceStopException();
            }
            request = new Message(PublicAPI.SORT_ROOMS_BY_STAR, new Object[]{rooms});
            writer.writeObject(request);
            reader.readObject();
            Printer.printEntities(rooms);
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }
}
