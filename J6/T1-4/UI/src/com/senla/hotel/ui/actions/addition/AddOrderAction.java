package com.senla.hotel.ui.actions.addition;

import java.util.GregorianCalendar;

import com.senla.hotel.constants.Messages;
import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.exceptions.NullException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.DateCreator;
import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class AddOrderAction implements IAction {

	@Override
	public void execute() throws ActionForceStopException {
		Printer.println(Messages.ASK_FOR_ORDER_PARAMS.toString());
		String[] params = Input.userInput().split(",");
		try {
			Facade.getInstance().addOrder(new Order(SelectRoomAction.getRoom().getID(),
					SelectClientAction.getClient().getID(), DateCreator.parseString(params[0].trim()),
					DateCreator.parseString(params[1].trim()), null), new GregorianCalendar().getTime());
		} catch (IncorrectParameterException | IncorrectIDEcxeption | NumberFormatException | NullException
				| ArrayIndexOutOfBoundsException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
			throw new ActionForceStopException();
		}
	}

}
