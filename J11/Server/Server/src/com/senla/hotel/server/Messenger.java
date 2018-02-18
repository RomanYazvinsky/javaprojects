package com.senla.hotel.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.hotel.facade.Facade;
import com.senla.hotel.message.Message;

public class Messenger {
	private static Logger logger = LogManager.getLogger(Messenger.class);


	@SuppressWarnings("rawtypes")
	public static Message execute(Message request) {
		Facade facade = Facade.getInstance();
		Object[] parameters = request.getData();
		Message response;
		try {
			if (parameters != null) {
				Class[] parameterTypes = new Class[parameters.length];
				for (int i = 0; i < parameters.length; i++) {
					parameterTypes[i] = parameters[i].getClass();
				}
				Method method = Facade.class.getMethod(request.getCommand(), parameterTypes);
				Object data = method.invoke(facade, parameters);
				response = new Message("OK", new Object[] { data });
				return response;
			} else {
				Method method = Facade.class.getMethod(request.getCommand(), null);
				Object data = method.invoke(facade, parameters);
				response = new Message("OK", new Object[] { data });
				return response;
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.debug(e);
			return new Message("Error", null);
		}
	}
}
