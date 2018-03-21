package com.senla.hotel.exceptions;

public class DatabaseConnectException extends Exception {
    public DatabaseConnectException() {
        super("Database is unaccessable");
    }
}
