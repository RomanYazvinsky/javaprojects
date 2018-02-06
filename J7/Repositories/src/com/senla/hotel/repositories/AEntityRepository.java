package com.senla.hotel.repositories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.IEntity;
import com.senla.hotel.exceptions.EmptyObjectException;

public abstract class AEntityRepository<T extends IEntity> {
	private static Logger logger;
	protected HashSet<T> entities;
	static {
		logger = Logger.getLogger(AEntityRepository.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.LOGFILE_HANDLER);
	}

	protected AEntityRepository() {
		entities = new HashSet<>();
	}

	public ArrayList<T> get() {
		return new ArrayList<T>(entities);
	}

	public abstract Boolean add(T entity, boolean addId);

	public Boolean delete(T entity) {
		return entities.remove(entity);
	}

	public T getByID(Integer id) {
		for (T entity : entities) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	public int getCount() {
		return entities.size();
	}

	public void set(ArrayList<T> entities) throws EmptyObjectException {
		if (entities == null) {
			EmptyObjectException e = new EmptyObjectException();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
		this.entities = new HashSet<>(entities);
	}
}
