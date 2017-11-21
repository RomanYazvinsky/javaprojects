package com.senla.hotel.comparators.order;

import java.util.Comparator;

import com.senla.hotel.entities.Order;
import com.senla.hotel.repositories.ClientRepository;

public class OrderClientNameComparator implements Comparator<Order> {
	private ClientRepository clientManager;

	public OrderClientNameComparator() {
		clientManager = ClientRepository.getInstance();
	}

	@Override
	public int compare(Order o1, Order o2) {
		if (o1 != null && o2 != null) {
			return clientManager.getByID(o1.getClientID()).getName().toLowerCase()
					.compareTo(clientManager.getByID(o2.getClientID()).getName().toLowerCase());
		} else {
			if (o1 != null && o2 == null) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
