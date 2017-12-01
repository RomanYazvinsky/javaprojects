package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.repositories.RoomRepository;

import utilities.Loader;
import utilities.Saver;

public class RoomWorker {
	private static Logger logger;
	private RoomRepository roomRepository;
	static {
		logger = Logger.getLogger(RoomWorker.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	public RoomWorker() {
		roomRepository = RoomRepository.getInstance();
	}

	public Boolean add(Room room) {
		return roomRepository.add(room);
	}

	public Boolean addNoIDGenerating(Room room) {
		return roomRepository.addNoIDGenerating(room);
	}

	public void load(String path) throws ClassNotFoundException, IOException, EmptyObjectException {
		try {
			roomRepository.setRooms(Loader.loadRooms(path));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public void save(String path) throws IOException {
		try {
			Saver.saveRooms(path, roomRepository.getRooms());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	public ArrayList<Room> getRooms() {
		return roomRepository.getRooms();
	}

	public Room getRoomByID(Integer roomID) {
		if (roomID == null) {
			return null;
		}
		return roomRepository.getByID(roomID);
	}

	public ArrayList<Room> sort(ArrayList<Room> rooms, Comparator<Room> comparator) {
		Collections.sort(rooms, comparator);
		return rooms;
	}

	public String[] toStringArray(ArrayList<Room> rooms) {
		List<String> result = new ArrayList<>();
		for (Room room : rooms) {
			result.add(room.toString());
		}
		return result.toArray(new String[result.size()]);
	}

	public void export(Room room) {
		roomRepository.export(room);
	}

	public Boolean delete(Room room) {
		return roomRepository.delete(room);
	}
}
