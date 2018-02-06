package utilities;

import java.util.HashSet;

public class IDGenerator {
	private static HashSet<Integer> clientIDs = new HashSet<>();
	private static HashSet<Integer> orderIDs = new HashSet<>();
	private static HashSet<Integer> roomIDs = new HashSet<>();
	private static HashSet<Integer> serviceIDs = new HashSet<>();

	public static Integer createClientID() {
		Integer i = IDGenerator.clientIDs.size();
		for (; !IDGenerator.clientIDs.add(i); i++) {
		}
		return i;
	}

	public static Integer createOrderID() {
		Integer i = IDGenerator.orderIDs.size();
		for (; !IDGenerator.orderIDs.add(i); i++) {
		}
		return i;
	}

	public static Integer createRoomID() {
		Integer i = IDGenerator.roomIDs.size();
		for (; !IDGenerator.roomIDs.add(i); i++) {
		}
		return i;
	}

	public static Integer createServiceID() {
		Integer i = IDGenerator.serviceIDs.size();
		for (; !IDGenerator.serviceIDs.add(i); i++) {
		}
		return i;
	}

	public static void addServiceID(int id) {
		IDGenerator.serviceIDs.add(id);
	}

	public static void addClientID(int id) {
		IDGenerator.clientIDs.add(id);
	}

	public static void addOrderID(int id) {
		IDGenerator.orderIDs.add(id);
	}

	public static void addRoomID(int id) {
		IDGenerator.roomIDs.add(id);
	}

}
