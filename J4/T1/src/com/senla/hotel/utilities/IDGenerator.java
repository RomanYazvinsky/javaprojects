package com.senla.hotel.utilities;

public class IDGenerator {
	private static Integer[] clientIDs = new Integer[10];
	private static Integer[] orderIDs = new Integer[10];
	private static Integer[] roomIDs = new Integer[10];
	private static Integer[] serviceIDs = new Integer[10];

	private static Boolean isRegistred(Integer[] array, int id) {
		for (int i = 0; i < ArrayWorker.getCount(array); i++) {
			if (array[i].equals(id)) {
				return true;
			}
		}
		return false;
	}

	public static Integer createClientID() {
		int count = ArrayWorker.getCount(IDGenerator.clientIDs);
		if (IDGenerator.clientIDs.length == count) {
			ArrayWorker.extendIntegerArray(IDGenerator.clientIDs);
		}
		int i = count;
		for (; IDGenerator.isRegistred(IDGenerator.clientIDs, i); i++) {
			IDGenerator.roomIDs[count] = i;
		}
		return i;
	}

	public static Integer createOrderID() {
		int count = ArrayWorker.getCount(IDGenerator.orderIDs);
		if (IDGenerator.orderIDs.length == count) {
			ArrayWorker.extendIntegerArray(IDGenerator.orderIDs);
		}
		int i = count;
		for (; IDGenerator.isRegistred(IDGenerator.orderIDs, i); i++) {
			IDGenerator.roomIDs[count] = i;
		}
		return i;
	}

	public static Integer createRoomID() {
		int count = ArrayWorker.getCount(IDGenerator.roomIDs);
		if (IDGenerator.roomIDs.length == count) {
			ArrayWorker.extendIntegerArray(IDGenerator.roomIDs);
		}
		int i = count;
		for (; IDGenerator.isRegistred(IDGenerator.roomIDs, i); i++) {
			IDGenerator.roomIDs[count] = i;
		}
		return i;
	}

	public static Integer createServiceID() {
		int count = ArrayWorker.getCount(IDGenerator.serviceIDs);
		if (IDGenerator.serviceIDs.length == count) {
			ArrayWorker.extendIntegerArray(IDGenerator.serviceIDs);
		}
		int i = count;
		for (; IDGenerator.isRegistred(IDGenerator.serviceIDs, i); i++) {
			IDGenerator.roomIDs[count] = i;
		}
		return i;
	}

	public static void addServiceID(int id) {
		int count = ArrayWorker.getCount(IDGenerator.serviceIDs);
		if (IDGenerator.serviceIDs.length == count) {
			ArrayWorker.extendIntegerArray(IDGenerator.serviceIDs);
		}
		IDGenerator.serviceIDs[count] = id;
	}

	public static void addClientID(int id) {
		int count = ArrayWorker.getCount(IDGenerator.clientIDs);
		if (IDGenerator.clientIDs.length == count) {
			ArrayWorker.extendIntegerArray(IDGenerator.clientIDs);
		}
		IDGenerator.clientIDs[count] = id;
	}

	public static void addOrderID(int id) {
		int count = ArrayWorker.getCount(IDGenerator.orderIDs);
		if (IDGenerator.orderIDs.length == count) {
			ArrayWorker.extendIntegerArray(IDGenerator.orderIDs);
		}
		IDGenerator.clientIDs[count] = id;
	}

	public static void addRoomID(int id) {
		int count = ArrayWorker.getCount(IDGenerator.roomIDs);
		if (IDGenerator.roomIDs.length == count) {
			ArrayWorker.extendIntegerArray(IDGenerator.roomIDs);
		}
		IDGenerator.roomIDs[count] = id;
	}

}
