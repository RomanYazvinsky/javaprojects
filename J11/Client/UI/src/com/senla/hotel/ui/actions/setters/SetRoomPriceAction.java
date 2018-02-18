package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.Input;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SetRoomPriceAction implements IAction {
    private static Logger logger = LogManager.getLogger(SetRoomPriceAction.class);


    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            SelectRoomAction.getRoom().setPricePerDay(Integer.parseInt(Input.userInput()));
        } catch (NumberFormatException | EmptyObjectException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
