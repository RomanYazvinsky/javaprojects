package com.senla.hotel.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.api.internal.IRoomWorker;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.repositories.RoomRepository;
import com.senla.hotel.utilities.CSVModule;

import utilities.Loader;
import utilities.Saver;

public class RoomWorker implements IRoomWorker   {
	private static Logger logger;
	private RoomRepository roomRepository;
	static {
		logger = Logger.getLogger(RoomWorker.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	public RoomWorker() {
		roomRepository = RoomRepository.getInstance();
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#add(com.senla.hotel.entities.Room, boolean)
	 */
	@Override
	public Boolean add(Room room, boolean addId) {
		return roomRepository.add(room, addId);
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#load(java.lang.String)
	 */
	@Override
	public void load(String path) throws ClassNotFoundException, IOException, EmptyObjectException {
		try {
			roomRepository.set(Loader.loadRooms(path));
		} catch (ClassNotFoundException | IOException | EmptyObjectException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#save(java.lang.String)
	 */
	@Override
	public void save(String path) throws IOException {
		try {
			Saver.saveRooms(path, roomRepository.get());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#getRooms()
	 */
	@Override
	public ArrayList<Room> getRooms() {
		return roomRepository.get();
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#getRoomByID(java.lang.Integer)
	 */
	@Override
	public Room getRoomByID(Integer roomID) {
		if (roomID == null) {
			return null;
		}
		return roomRepository.getByID(roomID);
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#sort(java.util.ArrayList, java.util.Comparator)
	 */
	@Override
	public ArrayList<Room> sort(ArrayList<Room> rooms, Comparator<Room> comparator) {
		Collections.sort(rooms, comparator);
		return rooms;
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#toStringArray(java.util.ArrayList)
	 */
	@Override
	public String[] toStringArray(ArrayList<Room> rooms) {
		List<String> result = new ArrayList<>();
		for (Room room : rooms) {
			result.add(room.toString());
		}
		return result.toArray(new String[result.size()]);
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#importAll()
	 */
	@Override
	public ArrayList<Room> importAll() throws EmptyObjectException {
		ArrayList<Room> rooms = new ArrayList<>();
		CSVModule.importAll(Room.class).forEach(new Consumer<Object>() {

			@Override
			public void accept(Object arg0) {
				rooms.add((Room) arg0);
			}
		});
		return rooms;
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#exportAll()
	 */
	@Override
	public void exportAll() {
		CSVModule.exportAll(getRooms());
	}

	/* (non-Javadoc)
	 * @see com.senla.hotel.workers.IRoomWorker#delete(com.senla.hotel.entities.Room)
	 */
	@Override
	public Boolean delete(Room room) {
		return roomRepository.delete(room);
	}
}
