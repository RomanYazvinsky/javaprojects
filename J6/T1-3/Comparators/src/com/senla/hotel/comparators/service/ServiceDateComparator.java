package com.senla.hotel.comparators.service;

import java.util.Comparator;

import com.senla.hotel.entities.Service;

public class ServiceDateComparator implements Comparator<Service> {
	@Override
	public int compare(Service o1, Service o2) {
		if (o1 != null && o2 != null) {
			return o1.getDate().compareTo(o2.getDate());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
