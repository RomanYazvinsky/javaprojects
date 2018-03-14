package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.api.ui.IAction;
import com.senla.hotel.exceptions.ActionForceStopException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddOrderServiceAction implements IAction {
    private static Logger logger = LogManager.getLogger(AddOrderServiceAction.class);

    @Override
    public void execute(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
          //  SelectOrderAction.getOrder().addService(SelectServiceAction.getService());
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new ActionForceStopException();

        }
    }

}
