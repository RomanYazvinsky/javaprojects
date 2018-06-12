package by.edu.server.exceptions;

public class DatabaseConnectException extends Exception {
    public DatabaseConnectException() {
        super("Database is unaccessable");
    }
}
