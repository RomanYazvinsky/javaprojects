package com.senla.hotel.api.internal;

import com.senla.hotel.entities.Room;
import com.senla.hotel.exceptions.EmptyObjectException;

import java.util.ArrayList;
import java.util.Comparator;

public interface IRoomWorker {

    Boolean add(Room room, boolean addId);

    ArrayList<Room> getRooms();

    Room getRoomByID(Integer roomID);

    ArrayList<Room> sort(ArrayList<Room> rooms, Comparator<Room> comparator);

    String[] toStringArray(ArrayList<Room> rooms);

    ArrayList<Room> importAll() throws EmptyObjectException;

    void exportAll();

    Boolean delete(Room room);

}