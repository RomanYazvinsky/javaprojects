package com.senla.hotel.entities;

public abstract class AEntity implements Comparable<AEntity> {
	protected Integer id;

	public AEntity() {
	}
	
	public AEntity(final int id) {
		this.id = id;
	}

	public Integer getID() {
		return id;
	}

	@Override
	public int compareTo(AEntity arg0) {
		if (arg0 != null) {
			return this.id.compareTo(arg0.getID());
		} else {
			return 1;
		}
	}
	
	
}
