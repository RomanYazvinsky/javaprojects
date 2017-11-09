package com.senla.hotel.idcomparators;

import java.util.Comparator;

import com.senla.hotel.entities.Order;

public class OrderIDComparator implements Comparator<Order> {

	@Override
	public int compare(Order o1, Order o2) {
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
