package com.senla.hotel.api.internal.dao;

import com.senla.hotel.exceptions.QueryFailureException;
import org.hibernate.Session;

public interface IGenericDao <T> {

    void create(Session session, T newInstance) throws QueryFailureException;

    T read(Session session, Integer id) throws QueryFailureException;

    void update(Session session, T transientObject) throws QueryFailureException;

    void delete(Session session, T persistentObject) throws QueryFailureException;
}
