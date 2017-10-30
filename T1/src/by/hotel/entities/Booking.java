package by.hotel.entities;

import java.util.Date;

public class Booking extends AEntity {
	private Room room;
	private Client client;
	private Date bookingFrom;
	private Date bookingTo;

	public Booking(Room room, Client client, Date bookingFrom) {
		this.room = room;
		this.client = client;
		this.bookingFrom = bookingFrom;
		this.bookingTo = null;
	}

	public Booking(Room room, Client client, Date bookingFrom, Date bookingTo) {
		this.room = room;
		this.client = client;
		this.bookingFrom = bookingFrom;
		this.bookingTo = bookingTo;
	}

	public Date getBookingTo() {
		return bookingTo;
	}

	public Boolean setBookingTo(Date bookingTo) {
		if (bookingFrom.after(bookingTo)) {
			return false;
		}
		this.bookingTo = bookingTo;
		return true;
	}

	public Room getRoom() {
		return room;
	}

	public Client getClient() {
		return client;
	}

	public Date getBookingFrom() {
		return bookingFrom;
	}

	public Boolean isDefined() {
		if (bookingTo == null) {
			return false;
		}
		return true;
	}

	public Boolean willExpire(Date date) {
		if (bookingTo.before(date)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookingFrom == null) ? 0 : bookingFrom.hashCode());
		result = prime * result + ((bookingTo == null) ? 0 : bookingTo.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
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
		Booking other = (Booking) obj;
		if (bookingFrom == null) {
			if (other.bookingFrom != null)
				return false;
		} else if (!bookingFrom.equals(other.bookingFrom))
			return false;
		if (bookingTo == null) {
			if (other.bookingTo != null)
				return false;
		} else if (!bookingTo.equals(other.bookingTo))
			return false;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}
}
