package by.hotel.managers;

import java.util.Arrays;
import java.util.Date;

import by.hotel.comparators.ClientComparators;
import by.hotel.entities.AEntity;
import by.hotel.entities.Client;

public class ClientManager extends AEntityManager {
	ClientComparators comparator;
	Client[] clientArray;

	public ClientManager() {
		super();
		clientArray = (Client[]) (entityArray = new Client[6]);
		comparator = new ClientComparators();
	}

	public Boolean addClient(Client client) {
		return addEntity(client);
	}

	private String[] makeStringArray() {
		String[] output = new String[entityCount];
		StringBuilder clientData = new StringBuilder();
		for (int i = 0; i < entityCount; i++) {
			clientData.append(clientArray[i].getName()).append(" ");
			if (clientArray[i].isActual()) {
				clientData.append(clientArray[i].getBooking().getBookingFrom()).append("->");
				if (clientArray[i].getBooking().isDefined()) {
					clientData.append(clientArray[i].getBooking().getBookingTo());
				} else {
					clientData.append("?");
				}
			} else {
				clientData.append("is not in hotel now");
			}
			output[i] = clientData.toString();
			clientData.delete(0, clientData.length());
		}
		return output;
	}

	private String[] doNotSort() {
		return makeStringArray();
	}

	private String[] sortByAlphabet() {
		Arrays.sort(clientArray, comparator.nameComparator);
		return makeStringArray();
	}

	private String[] sortByDate() {
		Arrays.sort(clientArray, comparator.dateComparator);
		return makeStringArray();
	}

	public String[] getListOfClients(int sortType, Date now) {
		switch (sortType) {
			case 0: {
				return doNotSort();
			}
			case 1: {
				return sortByAlphabet();
			}
			case 2: {
				return sortByDate();
			}
			default: {
				break;
			}
		}
		return null;
	}

	@Override
	protected int findEntity(AEntity entity) {
		for (int i = 0; i < entityCount; i++) {
			if (clientArray[i].equals((Client) entity)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	protected Boolean isCorrectType(AEntity entity) {
		return (entity instanceof Client);
	}

}
