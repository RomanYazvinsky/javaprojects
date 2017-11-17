package com.senla.hotel.ui.actions.addition;

import com.senla.hotel.entities.Order;
import com.senla.hotel.enums.Messages;
import com.senla.hotel.exceptions.IncorrectIDEcxeption;
import com.senla.hotel.exceptions.IncorrectParameterException;
import com.senla.hotel.exceptions.NullFieldException;
import com.senla.hotel.facade.Facade;
import com.senla.hotel.ui.actions.IAction;
import com.senla.hotel.ui.actions.selectors.SelectClientAction;
import com.senla.hotel.ui.actions.selectors.SelectRoomAction;

import utilities.DateCreator;
import utilities.IDGenerator;
import utilities.Input;
import utilities.LogWriter;
import utilities.Printer;

public class AddOrderAction implements IAction {

	@Override
	public void execute() {
		Printer.println(Messages.ASK_FOR_ORDER_PARAMS.toString());
		String[] params = Input.userInput().split(",");
		try {
			Facade.getInstance()
					.addOrder(new Order(IDGenerator.createOrderID(), SelectRoomAction.getRoom().getID(),
							SelectClientAction.getClient().getID(), DateCreator.parseString(params[0].trim()),
							DateCreator.parseString(params[1].trim()), null));
		} catch (IncorrectParameterException | IncorrectIDEcxeption | NumberFormatException | NullFieldException
				| ArrayIndexOutOfBoundsException e) {
			LogWriter.getInstance().log(e, this.getClass().getName());
		}
	}

}
