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
        try {
            Session session = sessionFactory.openSession();
            ArrayList<T> entities = new ArrayList<>();
            Criteria criteria = session.createCriteria(entityClass);
            entities = (ArrayList<T>) criteria.list();
            return entities;
        } catch (HibernateException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    protected List<T> getAll(Class<T> entityClass, SortType sortType) throws QueryFailureException {
            Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
                List<T> entities = (List<T>) session.createCriteria(entityClass).addOrder(Order.asc(defineSortType(sortType))).list();
                session.getTransaction().commit();
                return entities;
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                logger.log(Level.DEBUG, e.getMessage());
                throw new QueryFailureException();
            }
        }

    public void create(T entity) throws QueryFailureException{
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      try{
        session.save(entity);
        session.getTransaction().commit();
      }catch (HibernateException e){
          session.getTransaction().rollback();
          logger.log(Level.DEBUG, e.getMessage());
          throw new QueryFailureException();
      }
    }

    public void batchCreateOrUpdate(List<T> entities) throws QueryFailureException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
          for (int i =0; i< entities.size(); i++){
              session.saveOrUpdate(entities.get(i));
              if (i%20 ==0){
                  session.flush();
                  session.clear();
              }
          }
            transaction.commit();
        } catch (HibernateException e){
            transaction.rollback();
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
        finally {
            session.close();
        }
    }

    public void createOrUpdate(T entity) throws QueryFailureException{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        } catch (HibernateException e){
            session.getTransaction().rollback();
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public T read(Integer id, Class<T> entityClass) throws QueryFailureException{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            T entity = (T) session.createCriteria(entityClass).add(Restrictions.eq("id", id)).uniqueResult();
            session.getTransaction().commit();
            return entity;
        }catch (HibernateException e){
            session.getTransaction().rollback();
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void update(T entity) throws  QueryFailureException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.update(entity);
            session.getTransaction().commit();
        } catch (HibernateException e){
            session.getTransaction().rollback();
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }

    public void delete(T entity) throws QueryFailureException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.delete(entity);
            session.getTransaction().commit();
        } catch (HibernateException e){
            session.getTransaction().rollback();
            logger.log(Level.DEBUG, e.getMessage());
            throw new QueryFailureException();
        }
    }


}
