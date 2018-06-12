package by.edu.server.exceptions;

public class QueryFailureException extends Exception {
    public QueryFailureException() {
        super("Query contains incorrect values or syntax errors");
    }
}
