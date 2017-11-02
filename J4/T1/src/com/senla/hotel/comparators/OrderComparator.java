package com.senla.hotel.comparators;

import com.senla.hotel.comparators.order.OrderClientNameComparator;
import com.senla.hotel.comparators.order.OrderDateComparator;

public class OrderComparator {
	public OrderDateComparator dateComparator;
	public OrderClientNameComparator nameComparator;
	public OrderComparator() {
		dateComparator = new OrderDateComparator();
		nameComparator = new OrderClientNameComparator();
	}
}
