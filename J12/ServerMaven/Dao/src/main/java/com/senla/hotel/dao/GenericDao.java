package com.senla.hotel.dao;

import com.senla.hotel.api.internal.dao.IGenericDao;
import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.IEntity;
import com.senla.hotel.exceptions.QueryFailureException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static com.senla.hotel.constants.SQLConstants.*;

public abstract class GenericDao<T extends IEntity> implements IGenericDao<T> {

    private static Logger logger = LogManager.getLogger(GenericDao.class);
    protected SessionFactory sessionFactory;
    protected Class entityClass;

    protected GenericDao(SessionFactory sessionFactory, Class entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
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

    public List<T> getAll(Session session) throws QueryFailureException {
        try {
            List<T> entities;
            Criteria criteria = session.createCriteria(entityClass);
            entities = (List<T>) criteria.list();
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

    public List<T> getAll(Session session, SortType sortType) throws QueryFailureException {
        try {
            List<T> entities = (List<T>) session.createCriteria(entityClass).addOrder(Order.asc(defineSortType(sortType))).list();
            return entities;
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void create(Session session, T entity) throws QueryFailureException {
        try {
            session.save(entity);
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void createOrUpdate(Session session, T entity) throws QueryFailureException {
        try {
            session.saveOrUpdate(entity);
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }


    public T read(Session session, Integer id) throws QueryFailureException {
        try {
            T entity = (T) session.createCriteria(entityClass).add(Restrictions.eq("id", id)).list().get(0);
            return entity;
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void update(Session session, T entity) throws QueryFailureException {
        try {
            session.update(entity);
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void delete(Session session, T entity) throws QueryFailureException {
        try {
            session.delete(entity);
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }


}
