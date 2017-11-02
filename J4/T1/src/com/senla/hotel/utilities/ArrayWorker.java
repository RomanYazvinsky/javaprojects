package com.senla.hotel.utilities;

import java.util.Arrays;

import com.senla.hotel.entities.AEntity;
import com.senla.hotel.entities.Client;
import com.senla.hotel.entities.Order;
import com.senla.hotel.entities.Room;
import com.senla.hotel.entities.Service;

public class ArrayWorker {
	public static int getCount(AEntity[] array) {
		if (array == null) {
			return 0;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				return i;
			}
		}
		return array.length;
	}

	public static int getCount(Integer[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				return i;
			}
		}
		return array.length;
	}

	public static AEntity[] extendEntityArray(AEntity[] entities) {
		int entityCount = ArrayWorker.getCount(entities);
		return Arrays.copyOf(entities, entityCount + entityCount / 2);
	}
	
	public static void extendIntegerArray(Integer[] array) {
		int count = ArrayWorker.getCount(array);
		array = Arrays.copyOf(array, count+ count / 2);
	}

	public static AEntity[] decreaseArray(AEntity[] array) {
		return Arrays.copyOf(array, getCount(array));
	}

	public static Order[] castToOrder(AEntity[] entities) {
		//return Arrays.copyOf(entities, entities.length, Order[].class);
		return (Order[]) entities;
	}

	public static Room[] castToRoom(AEntity[] entities) {
	//	return Arrays.copyOf(entities, entities.length, Room[].class);
		return (Room[]) entities;
		
	}

	public static Client[] castToClient(AEntity[] entities) {
	//	return Arrays.copyOf(entities, entities.length, Client[].class);
		return (Client[]) entities;
	}
	
	public static Service[] castToService(AEntity[] entities) {
		return (Service[]) entities;
	}
	
	public static Service[] addUp(Service[] arr1, Service[] arr2) {
		int lengthArr1 = getCount(arr1);
		int lengthArr2 = getCount(arr2);
		Service[] result = new Service[lengthArr1+ lengthArr2];
		System.arraycopy(result, 0, arr1, 0, lengthArr1);
		System.arraycopy(result, lengthArr1, arr2, 0, lengthArr2);
		return result;
	}

}
