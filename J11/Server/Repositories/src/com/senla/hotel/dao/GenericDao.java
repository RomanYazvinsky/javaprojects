package com.senla.hotel.dao;

import com.senla.hotel.annotations.*;
import com.senla.hotel.api.internal.IFieldParser;
import com.senla.hotel.api.internal.IGenericDao;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.IEntity;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.injection.DependencyInjector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

import static com.senla.hotel.constants.SQLConstants.*;

public abstract class GenericDao<T extends IEntity> implements IGenericDao<T> {

    private static Logger logger = LogManager.getLogger(GenericDao.class);
    protected Connection connection;


    protected GenericDao(Connection connection) {
        this.connection = connection;
    }

    protected static String getTableName(Class entityClass) {
        Table table = (Table) entityClass.getAnnotation(Table.class);
        return table.tableName();
    }

    private String defineJoin(Class entityClass) {
        StringBuilder joins = new StringBuilder();
        int tableCounter = 0;
        for (Field field : entityClass.getDeclaredFields()) {
            ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
            if (foreignKey != null) {
                tableCounter++;
                String alias = STANDARD_TABLE_NAME + tableCounter;
                Class internalEntityClass = field.getType();
                String joinedTable = prepareGetQuery(internalEntityClass);
                joins.append(LEFT_JOIN).append(joinedTable).append(alias).append(ON)
                        .append(foreignKey.internalName()).append(EQUALS).append(alias).append(".").append(foreignKey.externalName()).append(" ) ");
            }
        }
        return joins.toString();
    }

    private String defineSortType(SortType sortType) {
        switch (sortType) {
            case ID: {
                return ORDER_BY_ID;
            }
            case DATE: {
                return ORDER_BY_DATE;
            }
            case NAME: {
                return ORDER_BY_NAME;
            }
        }
        return null;
    }

    private String getSequence(ArrayList<String> entityNames) throws UnexpectedValueException {
        if (entityNames.size() == 0) {
            try {
                throw new UnexpectedValueException();
            } catch (UnexpectedValueException e) {
                logger.log(Level.DEBUG, e);
                throw e;
            }
        }
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

    private ArrayList<String> getValueNames(T entity) throws UnexpectedValueException {
        Class entityClass = entity.getClass();
        ArrayList<Column> columns = new ArrayList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columns.add(column);
            }
        }
        ArrayList<String> valuesNames = new ArrayList<>();
        if (columns.size() == 0) {
            try {
                throw new UnexpectedValueException();
            } catch (UnexpectedValueException e) {
                logger.log(Level.DEBUG, e);
                throw e;
            }
        }
        for (Column column : columns) {
            valuesNames.add(column.fieldName());
        }
        return valuesNames;
    }

    private ArrayList<String> getValues(T entity, boolean replaceId) throws AnalysisException {
        Class entityClass = entity.getClass();
        ArrayList<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column != null) {


                Id id = field.getAnnotation(Id.class);
                if (id != null && id.auto() && !replaceId) {
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
                        throw new AnalysisException();
                    }
                }

                Getter getter = field.getAnnotation(Getter.class);
                value.delete(0, value.length());
                Object obj;
                try {
                    if (getter == null) {
                        obj = field.get(entity);
                    } else {
                        obj = field.get(entity);
                        obj = obj.getClass().getMethod(getter.getter(), null).invoke(obj, null);
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    logger.log(Level.DEBUG, e.getMessage());
                    throw new AnalysisException();
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

    private String getNewValueSequence(ArrayList<String> fieldNames, ArrayList<String> values) throws UnexpectedValueException {
        if (fieldNames.size() != values.size()) {
            throw new UnexpectedValueException();
        }
        ArrayList<String> newVals = new ArrayList<>();
        StringBuilder newVal = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            newVals.add(newVal.append(fieldNames.get(i)).append(EQUALS).append(values.get(i)).toString());
            newVal.delete(0, newVal.length());
        }
        try {
            return getSequence(newVals);
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e);
            throw e;
        }
    }

    private String prepareGetQuery(Class entityClass) {
        StringBuilder query = new StringBuilder(SELECT_FROM);
        Table table = (Table) entityClass.getAnnotation(Table.class);
        query.append(table.tableName()).append(defineJoin(entityClass)).append(") ");
        return query.toString();
    }

    protected ArrayList<T> getAll(Class<T> entityClass) throws QueryFailureException, UnexpectedValueException {
        try (Statement statement = connection.createStatement()) {
            ArrayList<T> entities = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(prepareGetQuery(entityClass) + ";");
            while (resultSet.next()) {
                entities.add(parseResult(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    protected ArrayList<T> getAll(Class<T> entityClass, SortType sortType) throws QueryFailureException, UnexpectedValueException {
        try (Statement statement = connection.createStatement()) {
            ArrayList<T> entities = new ArrayList<>();
            StringBuilder query = new StringBuilder(prepareGetQuery(entityClass));
            query.delete(query.length() - 2, query.length() - 1);
            query.append(defineSortType(sortType)).append(");");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                entities.add(parseResult(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;        }
    }

    protected abstract T parseResult(ResultSet resultSet) throws UnexpectedValueException;

    protected abstract String getTableName() throws AnalysisException;

    public void create(T entity) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        StringBuilder query = new StringBuilder(INSERT_INTO);
        try (Statement preparedStatement = connection.createStatement()) {
            query.append(getTableName());
            query.append(VALUES).append(getSequence(getValues(entity, false))).append(";");
            preparedStatement.executeUpdate(query.toString());
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        } catch (UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


    public void createOrUpdate(T entity) throws QueryFailureException, UnexpectedValueException, AnalysisException {
        StringBuilder query = new StringBuilder(REPLACE_INTO);
        ArrayList<String> values;
        try (Statement statement = connection.createStatement()) {
            ArrayList<String> fieldNames = getValueNames(entity);
            query.append(getTableName()).append(SET);
            values = getValues(entity, true);
            query.append(getNewValueSequence(fieldNames, values)).append(WHERE_ID).append(entity.getId()).append(";");
            statement.execute(query.toString());
        } catch (UnexpectedValueException | AnalysisException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public T read(Integer id, Class<T> entityClass) throws QueryFailureException, AnalysisException, UnexpectedValueException {
        try {
            StringBuilder query = new StringBuilder(prepareGetQuery(entityClass));
            query.delete(query.length() - 2, query.length() - 1);
            query.append(WHERE).append(getTableName()).append(".id = ?);");
            try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return parseResult(resultSet);
            } catch (UnexpectedValueException e) {
                logger.log(Level.DEBUG, e.getMessage());
                throw e;
            }
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        } catch (AnalysisException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    public void update(T entity) throws AnalysisException, QueryFailureException, UnexpectedValueException {
        StringBuilder query = new StringBuilder(UPDATE);
        query.append(getTableName()).append(SET);
        ArrayList<String> newVals = new ArrayList<>();
        ArrayList<String> values = null;
        try (Statement statement = connection.createStatement()) {
            ArrayList<String> fieldNames = getValueNames(entity);
            values = getValues(entity, false);
            query.append(getNewValueSequence(fieldNames, values)).append(WHERE_ID).append(entity.getId()).append(";");
            statement.execute(query.toString());
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        } catch (UnexpectedValueException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }

    }

    public void delete(T entity) throws AnalysisException, QueryFailureException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder query = new StringBuilder(DELETE_FROM);
            query.append(getTableName()).append(WHERE_ID).append(entity.getId()).append(";");
            statement.executeUpdate(query.toString());
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        } catch (AnalysisException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


}
