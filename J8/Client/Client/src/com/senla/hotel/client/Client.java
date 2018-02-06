package com.senla.hotel.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IMenuController;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.PropertyNames;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.properties.HotelProperties;

import utilities.DependencyInjector;

public class Client implements Runnable {

	public static void main(String[] args) {
		Client client;
		try {
			client = new Client();
			client.run();
		} catch (ConnectException e) {
			System.out.println("Server is offline");
		}
		
	}

	private static Logger logger;
	private IMenuController menuController;
	private Socket socket;
	private Integer port;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	static {
		logger = Logger.getLogger(Client.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	public Client() throws ConnectException {
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
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		} catch (IOException | EmptyObjectException | NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			menuController.setIO(writer, reader);
			menuController.run();
			writer.close();
			reader.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
}
