package com.senla.hotel.dao;

import com.senla.hotel.api.internal.dao.IRoomDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.DatabaseConnectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RoomDao extends GenericDao<Room> implements IRoomDao<Room> {
    private static Logger logger = LogManager.getLogger(RoomDao.class);

    public RoomDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory(), Room.class);
    }

}
