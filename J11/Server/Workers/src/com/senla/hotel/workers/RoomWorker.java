package com.senla.hotel.workers;

import com.senla.hotel.api.internal.IRoomWorker;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.DBConnector;
import utilities.DateCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class RoomWorker implements IRoomWorker {
    private static Logger logger = LogManager.getLogger(RoomWorker.class);
    private RoomDao roomDao;

    public RoomWorker() {
        roomDao = new RoomDao(DBConnector.getInstance().getConnection());
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IRoomWorker#add(com.senla.hotel.entities.Room, boolean)
     */
    @Override
    public synchronized Boolean add(Room room, boolean addId) {
        return roomDao.add(room);

    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IRoomWorker#load(java.lang.String)
     */
    @SuppressWarnings("unchecked")



    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IRoomWorker#save(java.lang.String)
     */

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IRoomWorker#getRooms()
     */
    @Override
    public ArrayList<Room> getRooms() {

        return roomDao.getAll();
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IRoomWorker#getRoomByID(java.lang.Integer)
     */
    @Override
    public Room getRoomByID(Integer roomID) {
        if (roomID == null) {
            return null;
        }
        return roomDao.read(roomID, Room.class);
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

    @Override
    public synchronized ArrayList<Room> importAll() throws EmptyObjectException {
        ArrayList<Room> rooms = new ArrayList<>();
        CSVModule.importAll(Room.class).forEach(new Consumer<Object>() {

            @Override
            public void accept(Object arg0) {
                rooms.add((Room) arg0);
            }
        });
        return rooms;
    }


    @Override
    public synchronized void exportAll() {
        CSVModule.exportAll(getRooms());
    }

    @Override
    public synchronized Boolean delete(Room room) {
        roomDao.delete(room);
        return true;
    }
}
