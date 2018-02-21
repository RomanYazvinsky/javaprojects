package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.dao.connector.DBConnector;
import com.senla.hotel.entities.*;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceRecordDao extends GenericDao<ServiceRecord> implements com.senla.hotel.api.internal.IServiceRecordDao<ServiceRecord> {
    private static Logger logger = LogManager.getLogger(ServiceRecordDao.class);

    public ServiceRecordDao() throws DatabaseConnectException {
        super(DBConnector.getInstance().getConnection());
    }

    @Override
    protected ServiceRecord parseResult(ResultSet resultSet) throws UnexpectedValueException {
        ServiceRecord serviceRecord = new ServiceRecord();
        try {
            Order order = new Order();
            Service service = new Service();
            Client client = new Client();
            Room room = new Room();

            serviceRecord.setId(resultSet.getInt("id"));
            serviceRecord.setDate(new java.util.Date(resultSet.getDate("date").getTime()));
            order.setId(resultSet.getInt("order_id"));

            client.setId(resultSet.getInt("client_id"));
            client.setName(resultSet.getString("name"));

            room.setId(resultSet.getInt("room_id"));
            room.setNumber(resultSet.getInt("number"));
            room.setCapacity(resultSet.getInt("capacity"));
            room.setStar(resultSet.getInt("star"));
            room.setPricePerDay(resultSet.getInt("price"));
            room.setStatus(RoomStatus.valueOf(resultSet.getString("status")));


            order.setClient(client);
            order.setRoom(room);
            Date dateFrom = resultSet.getDate("order_from");
            Date dateTo = resultSet.getDate("order_to");
            order.setOrderFrom(new java.util.Date(dateFrom.getTime()));
            if (dateTo != null) {
                order.setOrderTo(new java.util.Date(dateTo.getTime()));
            }

            service.setId(resultSet.getInt("service_id"));
            service.setPrice(resultSet.getInt("service_price"));
            service.setName(resultSet.getString("service_name"));

            serviceRecord.setOrder(order);
            serviceRecord.setService(service);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new UnexpectedValueException();
        }
        return serviceRecord;
    }

    @Override
    public boolean add(ServiceRecord serviceRecord) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        try {
            create(serviceRecord);
            return true;
        } catch (QueryFailureException | UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    protected String getTableName() throws AnalysisException {
        Table table = ServiceRecord.class.getAnnotation(Table.class);
        if (table == null) {
            throw new AnalysisException();
        }
        return table.tableName();
    }

    @Override
    public ArrayList<ServiceRecord> getAll() throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(ServiceRecord.class);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public ArrayList<ServiceRecord> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException {
        try {
            return getAll(ServiceRecord.class, sortType);
        } catch (QueryFailureException | UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;

        }
    }
}
