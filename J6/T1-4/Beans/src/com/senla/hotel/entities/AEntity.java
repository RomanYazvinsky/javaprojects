package com.senla.hotel.entities;

import java.io.Serializable;

import com.senla.hotel.exceptions.IncorrectIDEcxeption;

public abstract class AEntity implements Comparable<AEntity>, Serializable {
	private static final long serialVersionUID = 7423005803466459757L;
	protected Integer id;

	public AEntity() {
	}

	public AEntity(final int id) throws IncorrectIDEcxeption {
		if (id < 0) {
			throw new IncorrectIDEcxeption();
		}
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int compareTo(AEntity arg0) {
		if (arg0 != null) {
			return this.id.compareTo(arg0.getId());
		} else {
			return 1;
		}
	}

}
