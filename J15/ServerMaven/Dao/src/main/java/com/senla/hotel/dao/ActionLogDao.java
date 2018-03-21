package com.senla.hotel.dao;

import com.senla.hotel.api.internal.dao.IActionLogDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.ActionLog;
import com.senla.hotel.exceptions.DatabaseConnectException;

public class ActionLogDao extends GenericDao<ActionLog> implements IActionLogDao<ActionLog> {
    public ActionLogDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getSessionFactory(), ActionLog.class);
    }
}
