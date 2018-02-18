package com.senla.hotel.api.internal;

import com.senla.hotel.entities.IEntity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IGenericDao <T> {

    void create(T newInstance) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    T read(Integer id, Class<T> entityClass);

    void update(T transientObject) throws SQLException;

    void delete(T persistentObject) throws SQLException;
}
