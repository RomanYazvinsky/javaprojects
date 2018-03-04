package com.senla.hotel.exceptions;

public class UnexpectedValueException extends Exception {
    public UnexpectedValueException() {
        super("Method recieved unsupported set of arguments");
    }
}
