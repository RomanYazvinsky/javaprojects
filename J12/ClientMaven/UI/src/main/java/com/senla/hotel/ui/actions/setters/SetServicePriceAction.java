package com.senla.hotel.ui.actions.setters;

import com.senla.hotel.api.ui.IAction;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.ui.actions.selectors.SelectServiceAction;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Input;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SetServicePriceAction implements IAction {
    private static Logger logger = LogManager.getLogger(SetServicePriceAction.class);


    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            SelectServiceAction.getService().setPrice(Integer.parseInt(Input.userInput()));
        } catch (NumberFormatException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();
        }
    }

}
