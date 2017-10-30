package by.hotel.entities;

public class Client extends AEntity {
	private String name;
	private int id;
	private Booking actualBooking;

	public Client(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setBooking(Booking booking) {
		this.actualBooking = booking;
	}

	public Booking getBooking() {
		return actualBooking;
	}

	public boolean isActual() {
		return actualBooking == null ? false : true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
