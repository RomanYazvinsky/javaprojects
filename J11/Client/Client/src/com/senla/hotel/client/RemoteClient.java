package com.senla.hotel.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.hotel.api.internal.IMenuController;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.PropertyNames;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.properties.HotelProperties;

import utilities.injection.DependencyInjector;

public class RemoteClient {
	private static Logger logger = LogManager.getLogger(RemoteClient.class);

	public static void main(String[] args) {
		RemoteClient client;
		try {
			client = new RemoteClient();
			client.run();
		} catch (ConnectException e) {
			System.out.println("Server is offline");
			logger.log(Level.DEBUG, e.getMessage());
		}
		
	}

	private IMenuController menuController;
	private Socket socket;
	private Integer port;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;

	public RemoteClient() throws ConnectException {
		try {
			port = Integer.parseInt(HotelProperties.getInstance(Constants.PATH_TO_PROPERTIES)
					.getProperty(PropertyNames.PORT.toString()));
			socket = new Socket(
					HotelProperties.getInstance(Constants.PATH_TO_PROPERTIES).getProperty(PropertyNames.IP.toString()),
					port);
			menuController = (IMenuController) DependencyInjector.newInstance(IMenuController.class);
			writer = new ObjectOutputStream(socket.getOutputStream());
			InputStream inputStream = socket.getInputStream();
			reader = new ObjectInputStream(inputStream);

		} catch (ConnectException e) {
			logger.log(Level.DEBUG, e.getMessage());
			throw e;
		} catch (IOException | EmptyObjectException | NumberFormatException e) {
			logger.log(Level.DEBUG, e.getMessage());
		}
	}

	public void run() {
		try {
			menuController.setIO(writer, reader);
			menuController.run();
			writer.close();
			reader.close();
		} catch (IOException e) {
			logger.log(Level.DEBUG, e.getMessage());
		}
	}
}
