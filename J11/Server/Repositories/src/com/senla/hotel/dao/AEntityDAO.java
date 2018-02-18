package com.senla.hotel.dao;

import com.senla.hotel.annotations.*;
import com.senla.hotel.api.internal.IFieldParser;
import com.senla.hotel.api.internal.IGenericDao;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.IEntity;
import com.senla.hotel.entities.Order;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.DBConnector;
import utilities.DependencyInjector;
import utilities.Printer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

public abstract class AEntityDAO<T extends IEntity> implements IGenericDao<T> {
    private static final String DEFAULT = "DEFAULT";
    private static Logger logger = LogManager.getLogger(AEntityDAO.class);
    protected Connection connection;

    protected AEntityDAO(Connection connection) {
        this.connection = connection;
    }

    protected static String getTableName(Class entityClass) {
        Table table = (Table) entityClass.getAnnotation(Table.class);
        return table.tableName();
    }

    protected abstract T parseResult(ResultSet resultSet);

    protected abstract String getTableName();

    private String defineSortType(SortType sortType) {
        switch (sortType) {
            case ID: {
                return " ORDER BY id";
            }
            case DATE: {
                return " ORDER BY date";
            }
            case NAME: {
                return " ORDER BY name";
            }
        }
        return null;
    }

    private String getSequence(ArrayList<String> entityNames) {
        StringBuilder valuesNames = new StringBuilder("  ");
        for (int i = 0; i < entityNames.size(); i++) {
            valuesNames.append(entityNames.get(i));
            if (i < entityNames.size() - 1) {
                valuesNames.append(", ");
            } else {
                valuesNames.append("  ");
            }
        }
        return valuesNames.toString();
    }

    private ArrayList<String> getValueNames(T entity) {
        Class entityClass = entity.getClass();
        ArrayList<Column> columns = new ArrayList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column!=null){
                columns.add(column);
            }
        }
        ArrayList<String> valuesNames = new ArrayList<>();
        if (columns == null) {
            return null;
        }
        for (int i = 0; i < columns.size(); i++) {
            valuesNames.add(columns.get(i).fieldName());
        }
        return valuesNames;
    }

    private ArrayList<String> getValues(T entity) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class entityClass = entity.getClass();
        ArrayList<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        Field[] fields = entityClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            //field is column
            Column column = field.getAnnotation(Column.class);
            if (column != null) {


                //field is id or not, if id, check is it auto_increment --no check for specific getter, bcs its usually Integer
                Id id = field.getAnnotation(Id.class);
                if (id != null && id.auto()) {
                    values.add(DEFAULT);
                    continue;
                }

                Parseable parseable = field.getAnnotation(Parseable.class);
                if (parseable != null) {
                    IFieldParser fieldParser = (IFieldParser) DependencyInjector.newInstance(IFieldParser.class);
                    try {
                        Object obj = field.get(entity);
                        obj = fieldParser.getClass().getDeclaredMethod(parseable.methodName(), obj.getClass()).invoke(fieldParser, obj);
                        values.add(obj.toString());
                        continue;
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        logger.log(Level.DEBUG, e.getMessage());
                    }
                }

                // check for specified getter of class, for example, client
                Getter getter = field.getAnnotation(Getter.class);
                value.delete(0, value.length());
                Object obj = null;
                try {
                    if (getter == null) {
                        obj = field.get(entity);
                    } else {
                        obj = field.get(entity);
                        obj = obj.getClass().getMethod(getter.getter(), null).invoke(obj, null);
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    logger.log(Level.DEBUG, e.getMessage());
                    throw e;
                }
                if (column.isString()) {
                    value.append("'");
                    value.append(obj.toString());
                    value.append("' ");
                } else {
                    value.append(obj.toString());
                }
                values.add(value.toString());
            }
        }
        return values;
    }

    public void create(T entity) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(getTableName());
        try {
            query.append(" VALUES ").append(getSequence(getValues(entity))).append(";");
            Statement preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(query.toString());
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    private String defineJoin(Class entityClass) throws InvocationTargetException {
        StringBuilder joins = new StringBuilder();
        int tableCounter = 0;
        for (Field field : entityClass.getDeclaredFields()) {
            ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
            if (foreignKey != null) {
                tableCounter++;
                String alias = " tab" + tableCounter;
                Class internalEntityClass = field.getType();
                String joinedTable = prepareGetQuery(internalEntityClass);
                joins.append(" LEFT JOIN ").append(joinedTable).append(alias).append(" ON (")
                        .append(foreignKey.internalName()).append(" = ").append(alias).append(".").append(foreignKey.externalName()).append(" ) ");
            }
        }
        return joins.toString();
    }

    public T read(Integer id, Class<T> entityClass) {
        try {
            StringBuilder query = new StringBuilder(prepareGetQuery(entityClass));
            query.delete(query.length() - 2, query.length() - 1);
            query.append(" WHERE ").append(getTableName()).append(".id = ?);");
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return parseResult(resultSet);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return null;
    }

    public void update(T entity) throws SQLException {
        StringBuilder queue = new StringBuilder("UPDATE ");
        queue.append(getTableName()).append(" SET ");
        ArrayList<String> newVals = new ArrayList<>();
        ArrayList<String> valueNames = getValueNames(entity);
        ArrayList<String> values = null;
        try {
            values = getValues(entity);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        StringBuilder newVal = new StringBuilder();
        for (int i = 0; i < valueNames.size(); i++) {
            newVals.add(newVal.append(valueNames.get(i)).append(" = ").append(values.get(i)).toString());
            newVal.delete(0, newVal.length());
        }
        queue.append(getSequence(newVals)).append("WHERE id = ").append(entity.getId()).append(";");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queue.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    public void delete(T entity) {
        try {
            StringBuilder queue = new StringBuilder("DELETE FROM ");
            queue.append(getTableName()).append(" WHERE id = ").append(entity.getId()).append(";");
            PreparedStatement preparedStatement = connection.prepareStatement(queue.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
    }

    private String prepareGetQuery(Class entityClass) {
        StringBuilder query = new StringBuilder(" (SELECT * FROM ");
        try {
            Table table = (Table) entityClass.getAnnotation(Table.class);
            query.append(table.tableName()).append(defineJoin(entityClass)).append(") ");
        } catch (InvocationTargetException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return query.toString();
    }

    protected ArrayList<T> getAll(Class<T> entityClass) throws SQLException {
        try {
            ArrayList<T> entities = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(prepareGetQuery(entityClass).toString() + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entities.add(parseResult(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            // throw e;
            return null;
        }
    }

    protected ArrayList<T> getAll(Class<T> entityClass, SortType sortType) {
        try {
            ArrayList<T> entities = new ArrayList<>();
            StringBuilder query = new StringBuilder(prepareGetQuery(entityClass));
            query.delete(query.length() - 2, query.length() - 1);
            query.append(defineSortType(sortType)).append(");");
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entities.add(parseResult(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            return null;
        }
    }
}
