package com.senla.hotel.api.internal;

import com.senla.hotel.entities.IEntity;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IGenericDao <T> {

    void create(T newInstance) throws QueryFailureException, UnexpectedValueException, AnalysisException, com.senla.hotel.exceptions.QueryFailureException;

    T read(Integer id, Class<T> entityClass) throws QueryFailureException, AnalysisException, UnexpectedValueException;

    void update(T transientObject) throws SQLException, AnalysisException, QueryFailureException, UnexpectedValueException;

    void delete(T persistentObject) throws SQLException, AnalysisException, QueryFailureException;
}
