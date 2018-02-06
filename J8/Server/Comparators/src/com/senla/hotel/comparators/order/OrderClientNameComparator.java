package com.senla.hotel.comparators.order;

import java.util.Comparator;

import com.senla.hotel.entities.Order;

public class OrderClientNameComparator implements Comparator<Order> {

	@Override
	public int compare(Order o1, Order o2) {
		if (o1 != null && o2 != null) {
			return o1.getClient().getName().toLowerCase().compareTo(o2.getClient().getName().toLowerCase());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
