package by.hotel.comparators;

import java.util.Comparator;

import by.hotel.entities.Client;

public class ClientNameComparator implements Comparator<Client>{

	@Override
	public int compare(Client o1, Client o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
