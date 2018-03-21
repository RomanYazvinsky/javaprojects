package com.senla.hotel.exceptions;

public class AnalysisException extends Exception {
    public AnalysisException() {
        super("Error while doing an object reflection analysis");
    }
}
