package by.edu.server.exceptions;

public class EmptyObjectException extends Exception {


	private static final long serialVersionUID = 5095104434279780261L;

	public EmptyObjectException() {
		super("Some var is null");
	}
}
