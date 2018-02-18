package com.senla.hotel.dao;

import com.senla.hotel.annotations.Table;
import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceRecordDao extends AEntityDAO<ServiceRecord> {
    private static Logger logger = LogManager.getLogger(ServiceRecordDao.class);

    protected ServiceRecordDao(Connection connection) {
        super(connection);
    }

    @Override
    protected ServiceRecord parseResult(ResultSet resultSet) {
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
            if (dateTo!=null){
                order.setOrderTo(new java.util.Date(dateTo.getTime()));
            }

            service.setId(resultSet.getInt("service_id"));
            service.setPrice(resultSet.getInt("service_price"));
            service.setName(resultSet.getString("service_name"));

            serviceRecord.setOrder(order);
            serviceRecord.setService(service);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return serviceRecord;
    }

    public boolean add(ServiceRecord serviceRecord) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        try {
            create(serviceRecord);
            return true;
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        } catch (IllegalAccessException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;

        } catch (NoSuchMethodException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;

        } catch (InvocationTargetException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;

        }
        return false;
    }

    @Override
    protected String getTableName() {
        Table table = ServiceRecord.class.getAnnotation(Table.class);
        return table.tableName();
    }
}
