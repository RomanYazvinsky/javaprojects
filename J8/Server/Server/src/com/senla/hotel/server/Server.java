package com.senla.hotel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.PropertyNames;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.properties.HotelProperties;

import utilities.Printer;

public class Server {
	private static Logger logger;
	private Integer port;
	private ServerSocket serverSocket;
	static {
		logger = Logger.getLogger(Server.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	public Server() throws Exception {
		try {
			port = Integer.parseInt(HotelProperties.getInstance(Constants.PATH_TO_PROPERTIES)
					.getProperty(PropertyNames.PORT.toString()));
			serverSocket = new ServerSocket(port);
			Printer.print("Server is working");
		} catch (IOException | NumberFormatException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public void run() {
		try {
			while (true) {
				new Session(serverSocket.accept()).start();
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			System.out.println("Server is stopping");
		}
	}

	public static void main(String[] args) {
		try {
			new Server().run();
		} catch (Exception e) {

			System.out.println("Error");
		}
	}

}
