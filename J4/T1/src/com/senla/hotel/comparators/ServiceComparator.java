package com.senla.hotel.comparators;

import com.senla.hotel.comparators.service.ServiceDateComparator;
import com.senla.hotel.comparators.service.ServiceNameComparator;
import com.senla.hotel.comparators.service.ServicePriceComparator;

public class ServiceComparator {
	public ServicePriceComparator priceComparator;
	public ServiceDateComparator dateComparator;
	public ServiceNameComparator nameComparator;
	public ServiceComparator() {
		priceComparator = new ServicePriceComparator();
		dateComparator = new ServiceDateComparator();
		nameComparator = new ServiceNameComparator();
	}
}
