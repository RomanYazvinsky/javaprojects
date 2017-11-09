package com.senla.hotel.comarators.client;

import java.util.Comparator;

import com.senla.hotel.entities.Client;

public class ClientIDComparator implements Comparator<Client> {

	@Override
	public int compare(Client o1, Client o2) {
		if (o1 != null && o2 != null) {
			return o1.getID().compareTo(o2.getID());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
