package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Service;
import com.senla.hotel.entities.ServiceRecord;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDao extends AEntityDAO<Service> {
    private static Logger logger = LogManager.getLogger(ServiceDao.class);

    public ServiceDao(Connection connection) {
        super(connection);
    }

    public Service getById(int id) {
        return read(id, Service.class);
    }

    public Boolean add(Service service) {
        try {
            create(service);
            return true;
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.DEBUG, e.getMessage());
            return false;
        }
    }

    @Override
    protected Service parseResult(ResultSet resultSet) {
        Service service = new Service();
        try {
            service.setId(resultSet.getInt("id"));
            service.setPrice(resultSet.getInt("service_price"));
            service.setName(resultSet.getString("service_name"));
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }

        return service;
    }

    @Override
    protected String getTableName() {
        Table table = Service.class.getAnnotation(Table.class);
        return table.tableName();
    }

    public ArrayList<Service> getAll() {
        try {
            return getAll(Service.class);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            return null;
        }
    }

    public ArrayList<Service> getAll(SortType sortType) {
        return getAll(Service.class, sortType);
    }

    @Override
    public void delete(Service service) {
        try {
            connection.setAutoCommit(false);
            String s = "DELETE FROM " + AEntityDAO.getTableName(ServiceRecord.class) + " WHERE service_id = " + service.getId()+";";
            connection.createStatement().execute(s);
            super.delete(service);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.log(Level.DEBUG, e.getMessage());
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.log(Level.DEBUG, e.getMessage());
            }
        }
    }
}
