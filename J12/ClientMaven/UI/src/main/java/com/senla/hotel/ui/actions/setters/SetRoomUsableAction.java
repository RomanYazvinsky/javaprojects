package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.api.FacadeAPI;
import com.senla.hotel.api.ui.IAction;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.Room;
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

public class SetRoomUsableAction implements IAction {
    private static Logger logger = LogManager.getLogger(SetRoomUsableAction.class);


    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        Room room;
        try {
            room = SelectRoomAction.getRoom();
            if (room.isOnService()) {
                Message request = new Message(FacadeAPI.SET_ROOM_STATUS,
                        new Object[]{SelectRoomAction.getRoom(), RoomStatus.FREE_NOW});
                writer.writeObject(request);
                Message response = (Message) reader.readObject();

                Boolean result = (Boolean) response.getData()[0];
                if (!result) {
                    Printer.print("Access denied");
                }
            }
        } catch (EmptyObjectException | ClassNotFoundException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }

    }

}
