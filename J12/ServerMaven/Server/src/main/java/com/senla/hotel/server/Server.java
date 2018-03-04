package com.senla.hotel.server;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.constants.PropertyNames;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.properties.HotelProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ui.Printer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static Logger logger = LogManager.getLogger(Server.class);
	private Integer port;
	private ServerSocket serverSocket;
	private volatile boolean running;

	public Server() throws Exception {
		try {
			port = Integer.parseInt(HotelProperties.getInstance(Constants.PATH_TO_PROPERTIES)
					.getProperty(PropertyNames.PORT.toString()));
			serverSocket = new ServerSocket(port);
			Printer.print("Server is working");
			running = true;
			new ServerControl(this).start();
		} catch (IOException | NumberFormatException e) {
			logger.debug(e);
			throw e;
		}
	}

	public void run() {
		try {
			while (running) {
				Socket socket = serverSocket.accept();
				if (running) {
					new Session(socket).start();
				}
			}
		} catch (IOException e) {
			logger.debug(e);
		} finally {
			System.out.println("Server is stopping");
		}
	}

	public void stop(){
		running = false;
	}
	public void forceStop(){
		try {
			serverSocket.close();
			DBConnector.getInstance().close();
		} catch (IOException | DatabaseConnectException e) {
			logger.debug(e);
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
