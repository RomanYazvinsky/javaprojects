package com.senla.hotel.comparators.service;

import java.util.Comparator;

import com.senla.hotel.entities.Service;

public class ServicePriceComparator implements Comparator<Service> {
	@Override
	public int compare(Service o1, Service o2) {
		if (o1 != null && o2 != null) {
			return o1.getPrice().compareTo(o2.getPrice());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}

