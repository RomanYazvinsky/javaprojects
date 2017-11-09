package com.senla.hotel.comparators.order;

import java.util.Comparator;

import com.senla.hotel.entities.Order;

public class OrderDateComparator implements Comparator<Order>{

	@Override
	public int compare(Order o1, Order o2) {
		if (o1 != null && o2 != null) {
			return o1.getOrderTo().compareTo(o2.getOrderTo());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
