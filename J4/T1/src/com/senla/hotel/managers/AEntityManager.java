package com.senla.hotel.managers;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.utilities.ArrayWorker;

public abstract class AEntityManager {
	protected AEntity[] entities;

	protected abstract int find(AEntity entity);

	protected abstract Boolean isCorrectType(AEntity entity);

	protected abstract void extendArray();

	public Boolean add(AEntity entity) {
		int entityCount = ArrayWorker.getCount(entities);
		if (find(entity) >= 0 && entityCount > 0) {
			return false;
		}
		if (entityCount == entities.length) {
			extendArray();
		}
		entities[entityCount] = entity;
		return true;
	}

	public abstract int getCount();

	protected AEntity getByID(Integer id) {
		for (AEntity entity : entities) {
			if (entity != null) {
				if (entity.getID().equals(id))
					return entity;
			}
		}
		return null;
	}
}
