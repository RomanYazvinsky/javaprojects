package com.senla.hotel.entities;

public abstract class AEntity {
	protected Integer id;

	public AEntity() {

	}
	
	public AEntity(final int id) {
		this.id = id;
	}

	public Integer getID() {
		return id;
	}
}
