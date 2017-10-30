package by.hotel.comparators;

public class ClientComparators {
	public ClientDateComparator dateComparator;
	public ClientNameComparator nameComparator;
	public ClientComparators() {
		dateComparator = new ClientDateComparator();
		nameComparator = new ClientNameComparator();
	}
}
