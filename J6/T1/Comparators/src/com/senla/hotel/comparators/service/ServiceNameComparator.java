package com.senla.hotel.comparators.service;

import java.util.Comparator;

import com.senla.hotel.entities.Service;

public class ServiceNameComparator implements Comparator<Service> {

	@Override
	public int compare(Service o1, Service o2) {
		if (o1 != null && o2 != null) {
			return o1.getName().compareTo(o2.getName());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
