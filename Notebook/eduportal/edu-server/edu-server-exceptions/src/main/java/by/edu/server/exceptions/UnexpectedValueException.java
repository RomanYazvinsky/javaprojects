package by.edu.server.exceptions;

public class UnexpectedValueException extends Exception {
    public UnexpectedValueException() {
        super("Method recieved unsupported set of arguments");
    }
}
