package com.senla.hotel.repositories;

import java.io.IOException;

import com.senla.hotel.entities.AEntity;

public interface IEntityRepository {
	Boolean add(AEntity entity);

	int getCount();

	AEntity getByID(Integer id);

	Boolean delete(AEntity entity);

	public void save(String path) throws IOException;

	public void load(String path) throws IOException, ClassNotFoundException;

}
