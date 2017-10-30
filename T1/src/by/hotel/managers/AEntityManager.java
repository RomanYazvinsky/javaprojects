package by.hotel.managers;

import by.hotel.entities.AEntity;

public abstract class AEntityManager {
	protected AEntity[] entityArray;
	protected int entityCount;

	protected AEntityManager() {
		entityCount = 0;
	}

	protected abstract int findEntity(AEntity entity);

	protected abstract Boolean isCorrectType(AEntity entity);

	private void extendEntityArray() {
		AEntity[] newEntityArray = new AEntity[entityCount + entityCount / 3];
		System.arraycopy(entityArray, 0, newEntityArray, 0, entityCount);
		entityArray = newEntityArray;
	}

	protected Boolean addEntity(AEntity entity) {
		if (findEntity(entity) >= 0 && entityCount > 0) {
			return false;
		}
		if (entityCount == entityArray.length) {
			extendEntityArray();
		}
		entityArray[entityCount] = entity;
		entityCount++;
		return true;
	}
}
