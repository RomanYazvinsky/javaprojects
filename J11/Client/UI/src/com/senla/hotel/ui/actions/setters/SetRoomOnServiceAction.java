package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.api.PublicAPI;
import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.message.Message;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SetRoomOnServiceAction implements IAction {
    private static Logger logger = LogManager.getLogger(SetRoomOnServiceAction.class);

    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            Message request = new Message(PublicAPI.SET_ROOM_STATUS,
                    new Object[]{SelectRoomAction.getRoom(), RoomStatus.ONSERVICE_NOW});
            writer.writeObject(request);
            Message response = (Message) reader.readObject();

            Boolean result = (Boolean) response.getData()[0];
            if (!result) {
                Printer.print("Access denied");
            }

        } catch (EmptyObjectException | ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
