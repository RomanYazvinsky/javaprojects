package com.senla.hotel.repositories;

import com.senla.hotel.entities.AEntity;

public interface IEntityRepository {
	Boolean add(AEntity entity);

	int getCount();

	AEntity getByID(Integer id);

	Boolean delete(AEntity entity);

}
