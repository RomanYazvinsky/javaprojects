package com.senla.hotel.utilities;

public class IDGenerator {
	public static Integer[] clientIDs = new Integer[10];
	public static Integer[] orderIDs = new Integer[10];
	public static Integer[] roomIDs = new Integer[10];
	public static Integer[] serviceIDs = new Integer[10];
	
	public static Integer createID(Integer[] array) {
		int count = ArrayWorker.getCount(array);
		if (array.length == count) {
			ArrayWorker.extendIntegerArray(array);
		}
		array[count] = count;
		return count;
	}

}
