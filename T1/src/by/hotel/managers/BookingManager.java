package by.hotel.managers;

import java.util.Date;

import by.hotel.entities.AEntity;
import by.hotel.entities.Booking;
import by.hotel.entities.Client;
import by.hotel.entities.Room;
import by.hotel.entities.RoomStatus;

public class BookingManager extends AEntityManager {
	Booking[] bookingArray;

	public BookingManager() {
		super();
		bookingArray = (Booking[]) (entityArray = new Booking[6]);
	}

	public Boolean addBooking(Booking booking) {
		if(!booking.isDefined() && booking.getRoom().getStatus() == RoomStatus.USED) {
			return false;
		}
		return addEntity(booking);
	}

	private int searchByRoom(Room room, int startIndex) {
		for (int i = startIndex; i < entityCount; i++) {
			if (bookingArray[i].getRoom().equals(room))
				return i;
		}
		return -1;
	}

	@Override
	protected int findEntity(AEntity entity) {
		for (int i = 0; i < entityCount; i++) {
			if (bookingArray[i].equals((Booking) entity)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	protected Boolean isCorrectType(AEntity entity) {
		return (entity instanceof Booking);
	}

	public Booking getActualBooking(Client client, Date now) {
		int tempIndex = searchByClient(client, 0);
		while (tempIndex >= 0) {
			if (bookingArray[tempIndex].getBookingFrom().before(now)) {
				if (!bookingArray[tempIndex].isDefined() || bookingArray[tempIndex].getBookingTo().after(now)) {
					return bookingArray[tempIndex];
				}
			}
			tempIndex = searchByClient(client, tempIndex);
		}
		return null;
	}

	private int searchByClient(Client client, int startIndex) {
		for (int i = startIndex; i < entityCount; i++) {
			if (bookingArray[i].getClient().equals(client))
				return i;
		}
		return -1;
	}

	public String[] getLastClients(Room room, int clientCount) {
		String[] output = new String[clientCount];
		int tempIndex = 0;
		StringBuilder clientData = new StringBuilder();
		for (int i = 0; i < clientCount; i++) {
			tempIndex = searchByRoom(room, tempIndex);
			if (tempIndex >= 0) {
				clientData.append(bookingArray[tempIndex].getClient().getName()).append(" ")
						.append(bookingArray[tempIndex].getBookingFrom());
				if (bookingArray[tempIndex].isDefined()) {
					clientData.append("-").append(bookingArray[tempIndex].getBookingTo());
				}
				output[i] = clientData.toString();
				clientData.delete(0, clientData.length());
				tempIndex++;
			} else {
				break;
			}
		}
		return output;
	}

	public int priceOfBooking(Booking booking, Date now) {
		long dateDifferenceInMilliseconds = now.getTime() - booking.getBookingFrom().getTime();
		int days = (int) (dateDifferenceInMilliseconds / (24 * 60 * 60 * 1000));
		return days * booking.getRoom().getPricePerDay();
	}

	public void updateRoomClients(Date now) {
		for (int i = 0; i < entityCount; i++) {
			if (bookingArray[i].getRoom().getStatus() != RoomStatus.ONSERVICE) {
				if (!bookingArray[i].isDefined() || bookingArray[i].getBookingTo().after(now)) {
					int updateRoomResult = bookingArray[i].getRoom().addClient(bookingArray[i].getClient());
					if (updateRoomResult == -1) {
						bookingArray[i].getRoom().setStatus(RoomStatus.USED);
					} else {
						if (updateRoomResult == 0) {
							bookingArray[i].getRoom().setStatus(RoomStatus.PARTIALLY_FREE);
						}
					}
				}
				if (bookingArray[i].getRoom().getClientNumber() == 0) {
					bookingArray[i].getRoom().setStatus(RoomStatus.FREE);
				}
			}
		}
	}

	public void updateClientBookings(Date now) {
		for (int i = 0; i < entityCount; i++) {
			bookingArray[i].getClient().setBooking(getActualBooking(bookingArray[i].getClient(), now));
		}
	}

}
