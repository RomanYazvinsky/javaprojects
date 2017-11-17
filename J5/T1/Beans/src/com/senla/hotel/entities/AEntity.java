package com.senla.hotel.entities;

import com.senla.hotel.exceptions.IncorrectIDEcxeption;

public abstract class AEntity implements Comparable<AEntity> {
	protected Integer id;

	public AEntity() {
	}

	public AEntity(final int id) throws IncorrectIDEcxeption {
		if (id < 0) {
			throw new IncorrectIDEcxeption();
		}
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
