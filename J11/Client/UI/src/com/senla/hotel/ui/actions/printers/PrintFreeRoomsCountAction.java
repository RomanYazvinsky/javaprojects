package com.senla.hotel.ui.actions.printers;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;

public class PrintFreeRoomsCountAction implements IAction {
    private static Logger logger = LogManager.getLogger(PrintFreeRoomsCountAction.class);


    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            Message request = new Message(PublicAPI.GET_FREE_ROOMS_COUNT, new Object[]{new GregorianCalendar().getTime()});
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            Integer count = (Integer) response.getData()[0];
            Printer.print(count.toString());

        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
