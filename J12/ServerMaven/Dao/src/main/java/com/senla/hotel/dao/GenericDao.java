package com.senla.hotel.dao;

import com.senla.hotel.api.internal.IGenericDao;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.IEntity;
import com.senla.hotel.exceptions.QueryFailureException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

import static com.senla.hotel.constants.SQLConstants.*;

public abstract class GenericDao<T extends IEntity> implements IGenericDao<T> {

    private static Logger logger = LogManager.getLogger(GenericDao.class);
    protected SessionFactory sessionFactory;


    protected GenericDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

    protected ArrayList<T> getAll(Class<T> entityClass) throws QueryFailureException {
        Session session = sessionFactory.getCurrentSession();
        try {
            ArrayList<T> entities;
            Criteria criteria = session.createCriteria(entityClass);
            entities = (ArrayList<T>) criteria.list();
            return entities;
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }

    }

    public List<?> get(Criteria criteria) throws QueryFailureException {
        try {
            List<?> entities = (List<?>) criteria.list();
            return entities;
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public List<T> getAll(Class<T> entityClass, SortType sortType) throws QueryFailureException {
        Session session = sessionFactory.getCurrentSession();
        try {
            List<T> entities = (List<T>) session.createCriteria(entityClass).addOrder(Order.asc(defineSortType(sortType))).list();
            return entities;
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void create(T entity) throws QueryFailureException {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(entity);
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void createOrUpdate(T entity) throws QueryFailureException {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(entity);
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }


    public T read(Integer id, Class<T> entityClass) throws QueryFailureException {
        Session session = sessionFactory.getCurrentSession();
        try {
            T entity = (T) session.createCriteria(entityClass).add(Restrictions.eq("id", id)).list().get(0);
            return entity;
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void update(T entity) throws QueryFailureException {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(entity);
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void delete(T entity) throws QueryFailureException {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.delete(entity);
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }


}
