package com.senla.hotel.comparators.client;

import java.util.Comparator;

import com.senla.hotel.entities.Client;

public class ClientNameComparator implements Comparator<Client> {
	@Override
	public int compare(Client o1, Client o2) {
		if (o1 != null && o2 != null) {
			return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
