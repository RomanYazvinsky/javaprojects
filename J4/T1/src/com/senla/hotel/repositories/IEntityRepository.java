package com.senla.hotel.repositories;

import com.senla.hotel.entities.AEntity;

public interface IEntityRepository {
	Boolean add(AEntity entity);

	int getCount();

	int getIndex(AEntity entity);
	
	AEntity getByID(Integer id);
}
