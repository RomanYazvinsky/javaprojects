package com.senla.hotel.managers;

import com.senla.hotel.dao.ActionLogDao;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.ActionLog;
import com.senla.hotel.entities.User;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

public class ActionLogManager extends GenericManager<ActionLog> implements com.senla.hotel.api.internal.managers.IActionLogManager {
    private static Logger logger = LogManager.getLogger(ActionLogManager.class);

    public ActionLogManager() throws DatabaseConnectException, QueryFailureException {
        super(new ActionLogDao());
    }

    public void log(User user, String message) throws QueryFailureException, DatabaseConnectException {
        Date date = new Date();
        ActionLog log = new ActionLog();
        log.setUser(user);
        log.setDate(date);
        log.setMessage(message);
        Transaction transaction = null;
        try {
            Session session = DBConnector.getInstance().getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            dao.create(session, log);
            transaction.commit();
        } catch (QueryFailureException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.DEBUG, e);
            throw e;
        } catch (DatabaseConnectException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }
}
