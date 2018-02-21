package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDao extends GenericDao<Service> implements com.senla.hotel.api.internal.IServiceDao<Service> {
    private static Logger logger = LogManager.getLogger(ServiceDao.class);

    public ServiceDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getConnection());
    }

    @Override
    public Service getById(int id) throws QueryFailureException, AnalysisException, UnexpectedValueException {
        try {
            return read(id, Service.class);
        } catch (QueryFailureException | AnalysisException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Boolean add(Service service) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            create(service);
            return true;
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    protected Service parseResult(ResultSet resultSet) throws UnexpectedValueException {
        Service service = new Service();
        try {
            service.setId(resultSet.getInt("id"));
            service.setPrice(resultSet.getInt("service_price"));
            service.setName(resultSet.getString("service_name"));
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new UnexpectedValueException();
        }

        return service;
    }

    @Override
    protected String getTableName() throws AnalysisException {
        Table table = Service.class.getAnnotation(Table.class);
        if (table == null) {
            throw new AnalysisException();
        }
        return table.tableName();
    }

    @Override
    public ArrayList<Service> getAll() throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(Service.class);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<Service> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(Service.class, sortType);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;

        }
    }


}
