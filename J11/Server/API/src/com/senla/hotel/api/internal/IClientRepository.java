package com.senla.hotel.api.internal;

import java.util.ArrayList;

import com.senla.hotel.entities.Client;
import com.senla.hotel.exceptions.EmptyObjectException;

public interface IClientRepository {

	ArrayList<Client> get();

	Boolean add(Client entity, boolean addId);

	Boolean delete(Client entity);

	Client getByID(Integer id);

	int getCount();

	void set(ArrayList<Client> entities) throws EmptyObjectException;
}