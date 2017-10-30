package by.hotel.comparators;

import java.util.Comparator;

import by.hotel.entities.Client;

public class ClientDateComparator implements Comparator<Client>{

	@Override
	public int compare(Client o1, Client o2) {
		return o1.getBooking().getBookingTo().compareTo(o2.getBooking().getBookingTo());
	}

}
