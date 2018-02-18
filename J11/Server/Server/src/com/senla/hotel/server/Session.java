package com.senla.hotel.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.hotel.message.Message;

public class Session extends Thread {
	private static Logger logger = LogManager.getLogger(Session.class);
	private Socket socket;

	public Session(Socket socket) {
		this.socket = socket;
		setDaemon(true);
		setPriority(NORM_PRIORITY);
	}

	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			ObjectInputStream deserializer = new ObjectInputStream(inputStream);

			ObjectOutputStream serializer  =new ObjectOutputStream(outputStream);
			while (true) {		
				Message request = (Message) deserializer.readObject();
				Message response = Messenger.execute(request);
				
				serializer.writeObject(response);
			}
		} catch (IOException | ClassNotFoundException e) {
			logger.debug(e);
		}

	}
}
